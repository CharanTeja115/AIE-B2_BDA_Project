import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window

object Preprocessing {
  def main(args: Array[String]): Unit = {

    // 1️⃣ Create Spark session
    val spark = SparkSession.builder()
      .appName("AirQuality_Preprocessing")
      .master("local[*]")   // Remove this when running on cluster
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    // 2️⃣ Load dataset from HDFS
    val inputPath = "hdfs://namenode:8020/BDA1/china-AQ-dataset.csv"

    val df = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(inputPath)

    println("✅ Initial Dataset Loaded:")
    df.show(5)
    df.printSchema()

    // 3️⃣ Drop unwanted columns
    val df1 = df.drop("location", "lat", "lon", "location_en")

    // 4️⃣ Convert datetime column to timestamp
    val df2 = df1.withColumn("datetime", to_timestamp(col("datetime")))

    // 5️⃣ Sort by location_id and datetime
    val df3 = df2.orderBy("location_id", "datetime")

    // 6️⃣ Forward fill missing pollutant values
    val pollutants = Seq("pm10", "pm25", "o3", "co", "no2", "so2")

    val windowSpec = Window
      .partitionBy("location_id")
      .orderBy("datetime")
      .rowsBetween(Window.unboundedPreceding, Window.currentRow)

    val dfFilled = pollutants.foldLeft(df3) { (tempDF, colName) =>
      tempDF.withColumn(
        colName,
        last(col(colName), ignoreNulls = true).over(windowSpec)
      )
    }

    // 7️⃣ Extract time features
    val dfWithTime = dfFilled
      .withColumn("hour", hour(col("datetime")))
      .withColumn("day", dayofmonth(col("datetime")))
      .withColumn("month", month(col("datetime")))
      .withColumn("weekday", dayofweek(col("datetime")))

    // 8️⃣ Drop remaining nulls
    val dfClean = dfWithTime.na.drop()

    // 9️⃣ Clip extreme values
    val dfClipped = pollutants.foldLeft(dfClean) { (tempDF, colName) =>
      tempDF.withColumn(
        colName,
        when(col(colName) > 1000, 1000)
          .when(col(colName) < 0, 0)
          .otherwise(col(colName))
      )
    }

    // 🔟 Scale pollutant values
    val dfScaled = pollutants.foldLeft(dfClipped) { (tempDF, colName) =>
      tempDF.withColumn(colName, col(colName) / 1000)
    }

    // 11️⃣ Save to HDFS
    val outputPath = "hdfs://namenode:8020/BDA1/processed_china_AQ"

    dfScaled
      .coalesce(1)
      .write
      .mode("overwrite")
      .option("header", "true")
      .csv(outputPath)

    println(s"✅ Preprocessing complete! Saved at: $outputPath")

    spark.stop()
  }
}