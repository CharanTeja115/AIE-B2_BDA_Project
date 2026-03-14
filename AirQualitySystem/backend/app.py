from fastapi import FastAPI
from pyspark.sql import SparkSession
from pyspark.ml.feature import VectorAssembler
from pyspark.ml.regression import RandomForestRegressionModel

app = FastAPI()

spark = SparkSession.builder \
    .appName("AQ Backend") \
    .master("spark://spark-master:7077") \
    .getOrCreate()

@app.get("/")
def home():
    return {"status": "Backend Running"}

@app.post("/predict")
def predict(data: dict):

    pollutant = data["pollutant"]

    values = [(
        data["pm10"],
        data["pm25"],
        data["o3"],
        data["co"],
        data["no2"],
        data["so2"],
        data["hour"],
        data["location_id"]
    )]

    columns = ["pm10","pm25","o3","co","no2","so2","hour","location_id"]

    df = spark.createDataFrame(values, columns)

    assembler = VectorAssembler(
        inputCols=columns,
        outputCol="features"
    )

    df = assembler.transform(df)

    model = RandomForestRegressionModel.load(
        f"hdfs://namenode:8020/BDA1/Models/{pollutant}_model"
    )

    prediction = model.transform(df)

    result = prediction.select("prediction").collect()[0][0]

    return {"prediction": float(result)}