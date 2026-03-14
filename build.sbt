name := "AirQualityProject"

version := "0.1"

scalaVersion := "2.12.18"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core"  % "3.4.1" % "provided",
  "org.apache.spark" %% "spark-sql"   % "3.4.1" % "provided",
  "org.apache.spark" %% "spark-mllib" % "3.4.1" % "provided",
  "org.scalanlp" %% "breeze" % "2.1.0",
  "org.scalanlp" %% "breeze-viz" % "2.1.0"
)