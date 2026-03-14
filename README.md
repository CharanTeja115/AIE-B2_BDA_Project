# Real-Time Air Quality Monitoring and Pollution Prediction

## Project Overview

This project presents a **Big Data analytics framework for air quality monitoring and pollution prediction**.
The system uses **Apache Hadoop (HDFS)** for distributed storage and **Apache Spark** for large-scale data processing and machine learning.

A **Random Forest regression model using Spark MLlib** is applied to predict pollutant concentrations such as PM2.5, PM10, NO₂, SO₂, CO, and O₃ based on historical environmental data.

---

## Technologies Used

* Apache Hadoop (HDFS)
* Apache Spark
* Spark MLlib
* Scala
* Python
* Docker
* Streamlit (Visualization Dashboard)

---
---

## Project Structure

```
AIE-B2_BDA_Project
│
├── AirQualitySystem
│   ├── backend
│   ├── dashboard
│   └── docker-compose.yml
│
├── src
│   └── main
│       └── scala
│           ├── Preprocessing.scala
│           └── TrainAirQualityModel.scala
│
├── project
├── build.sbt
├── B2.pdf
├── 22AIE312_BIG-DATA_ANALYTICS_B2.pdf
└── README.md
```

---
## Dataset

The project uses a large air quality dataset containing pollutant measurements and environmental attributes including:

* PM2.5
* PM10
* NO₂
* SO₂
* CO
* O₃
* Temperature
* Humidity
* Wind Speed

Dataset Source:
https://zenodo.org/records/17085064

---

## System Architecture

The system pipeline consists of:

1. **Data Ingestion**

   * Air quality dataset downloaded from OpenAQ / cloud storage
   * Stored in **Hadoop HDFS**

2. **Data Preprocessing**

   * Handling missing values
   * Timestamp processing
   * Feature extraction using Spark DataFrames

3. **Model Training**

   * Random Forest Regression using **Spark MLlib**

4. **Model Evaluation**

   * Metrics used: **R² Score and MAE**

5. **Visualization**

   * Streamlit dashboard to visualize prediction performance


## Documentation

This repository also contains the complete project documentation:

* **Project Report:**
  `B2.pdf`

* **Project Presentation (PPT converted to PDF):**
  `22AIE312_BIG-DATA_ANALYTICS_B2.pdf`

These documents explain the **problem statement, methodology, system architecture, dataset, implementation, and results** of the project.

---

## Results

The Random Forest models achieved strong prediction accuracy for most pollutants, demonstrating the effectiveness of **distributed machine learning for air quality forecasting**.

Example R² Scores:

| Pollutant | R² Score |
| --------- | -------- |
| PM10      | 0.873    |
| PM2.5     | 0.857    |
| O₃        | 0.812    |
| CO        | 0.785    |
| NO₂       | 0.851    |
| SO₂       | 0.834    |

---

## Future Improvements

* Integration with **real-time IoT air quality sensors**
* Real-time streaming using **Apache Kafka / Spark Streaming**
* Advanced deep learning models for prediction
* Web-based air quality monitoring dashboard

---

## Authors

Group 2

* Rahul B
* Karthikeya CH
* Mohan Raj S
* Charan Teja G

Amrita School of Artificial Intelligence
Amrita Vishwa Vidyapeetham
