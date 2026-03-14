# Air Quality Prediction System using Big Data Analytics

## Project Overview

This project focuses on analyzing and predicting air pollution levels using **Big Data technologies**. The system processes large air quality datasets using **Apache Spark and Scala**, performs data preprocessing, and trains machine learning models to predict pollution levels.

The project also includes a **backend service and visualization dashboard** to monitor air quality metrics.

---

## Objectives

* Analyze air pollution datasets using Big Data tools
* Perform data preprocessing and feature engineering
* Train a machine learning model for air quality prediction
* Visualize pollution trends and predictions
* Deploy the system using Docker containers

---

## Technologies Used

* **Apache Spark**
* **Scala**
* **SBT (Scala Build Tool)**
* **Python (Backend API)**
* **Docker**
* **Machine Learning**
* **Big Data Analytics**

---

## Project Architecture

The system consists of three main components:

1. **Data Processing Layer**

   * Implemented using **Apache Spark with Scala**
   * Performs data cleaning and preprocessing
   * Handles large-scale air pollution datasets

2. **Machine Learning Layer**

   * Trains predictive models for air quality levels
   * Uses historical pollution data for predictions

3. **Application Layer**

   * Backend API for serving predictions
   * Dashboard for visualizing pollution metrics

---

## Project Structure

```
AIE-B2_BDA_Project
│
├── AirQualitySystem
│   ├── backend
│   │   ├── app.py
│   │   └── Dockerfile
│   │
│   ├── dashboard
│   │   ├── dashboard.py
│   │   └── Dockerfile
│   │
│   └── docker-compose.yml
│
├── src
│   └── main
│       └── scala
│           ├── Preprocessing.scala
│           └── TrainAirQualityModel.scala
│
├── project
│   └── build.properties
│
├── build.sbt
└── README.md
```

---

## Dataset

The dataset used in this project contains various air pollution indicators such as:

* PM2.5
* PM10
* NO₂
* SO₂
* CO
* O₃

These pollutants are used to analyze air quality levels and train predictive models.

---

## Key Features

* Big Data processing using **Apache Spark**
* Data preprocessing and cleaning
* Air pollution prediction model
* Visualization dashboard
* Containerized deployment using **Docker**

---

## How to Run the Project

### Step 1: Clone the Repository

```
git clone https://github.com/CharanTeja115/AIE-B2_BDA_Project.git
```

### Step 2: Navigate to the Project Folder

```
cd AIE-B2_BDA_Project
```

### Step 3: Build the Scala Project

```
sbt compile
```

### Step 4: Run Spark Processing

```
sbt run
```

### Step 5: Run the Docker System

```
docker-compose up
```

---

## Future Improvements

* Integration with real-time pollution data sources
* Advanced machine learning models for prediction
* Web-based interactive dashboard
* Real-time alerts for high pollution levels

---

## Authors

**Charan Teja**

Big Data Analytics Project
Apache Spark + Scala Implementation

---

## License

This project is developed for **educational and research purposes**.
