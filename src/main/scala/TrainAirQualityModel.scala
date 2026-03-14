import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.regression.RandomForestRegressor
import org.apache.spark.ml.evaluation.RegressionEvaluator

object TrainAirQualityModel {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("AirQualityModel_Training")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    // Correct HDFS path
    val inputPath = "hdfs://namenode:8020/BDA1/processed_china_AQ"

    val df = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(inputPath)

    println("✅ Dataset Loaded Successfully")

    val pollutants = Seq("pm10", "pm25", "o3", "co", "no2", "so2")

    val windowSpec = Window.partitionBy("location_id").orderBy("datetime")

    val dfTargets = pollutants.foldLeft(df) { (tempDF, colName) =>
      tempDF.withColumn(s"${colName}_next", lead(col(colName), 1).over(windowSpec))
    }.na.drop()

    val featureCols = Array("pm10", "pm25", "o3", "co", "no2", "so2", "hour", "location_id")

    val assembler = new VectorAssembler()
      .setInputCols(featureCols)
      .setOutputCol("features")

    val evaluatorR2 = new RegressionEvaluator()
      .setLabelCol("label")
      .setPredictionCol("prediction")
      .setMetricName("r2")

    pollutants.foreach { pol =>

      println(s"🔹 Training model for $pol")

      val dfPrepared = assembler.transform(dfTargets)
        .select("features", s"${pol}_next")
        .withColumnRenamed(s"${pol}_next", "label")

      val Array(train, test) = dfPrepared.randomSplit(Array(0.8, 0.2), seed = 42)

      val rf = new RandomForestRegressor()
        .setLabelCol("label")
        .setFeaturesCol("features")
        .setNumTrees(100)
        .setMaxDepth(8)

      val model = rf.fit(train)
      val predictions = model.transform(test)

      val r2 = evaluatorR2.evaluate(predictions)

      println(f"✅ $pol%-5s | R² = $r2%1.4f")

      val outputPath = s"hdfs://namenode:8020/BDA1/Models/${pol}_model"
      model.write.overwrite().save(outputPath)
    }

    println("🎯 All models trained successfully!")
    spark.stop()
  }
}