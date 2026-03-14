import streamlit as st
import requests
import plotly.express as px
import pandas as pd

# ------------------------------------------------
# PAGE CONFIG
# ------------------------------------------------
st.set_page_config(
    page_title="Air Quality Prediction System",
    page_icon="🌍",
    layout="wide"
)

st.title("🌍 Air Quality Prediction System")
st.markdown("### Distributed Big Data & Machine Learning (Spark + HDFS + FastAPI)")

# ------------------------------------------------
# MODEL PERFORMANCE DATA
# ------------------------------------------------
performance = pd.DataFrame({
    "Pollutant": ["PM10","PM25","O3","CO","NO2","SO2"],
    "R² Score": [0.8489,0.8686,0.9591,0.8633,0.8461,0.6226]
})

# ------------------------------------------------
# TABS
# ------------------------------------------------
tab1, tab2 = st.tabs(["📊 Model Performance", "🔮 Live Prediction"])

# =================================================
# TAB 1 — MODEL PERFORMANCE
# =================================================
with tab1:

    st.subheader("Model Accuracy Comparison")

    col1, col2 = st.columns(2)

    col1.dataframe(performance, use_container_width=True)

    fig = px.bar(
        performance,
        x="Pollutant",
        y="R² Score",
        color="R² Score",
        text="R² Score",
        template="plotly_white"
    )

    col2.plotly_chart(fig, use_container_width=True)

    st.markdown("---")

    c1,c2,c3,c4,c5,c6 = st.columns(6)

    c1.metric("PM10", "0.8489")
    c2.metric("PM25", "0.8686")
    c3.metric("O3", "0.9591")
    c4.metric("CO", "0.8633")
    c5.metric("NO2", "0.8461")
    c6.metric("SO2", "0.6226")


# =================================================
# TAB 2 — LIVE PREDICTION
# =================================================
with tab2:

    st.subheader("Real-Time Air Quality Prediction")

    col1, col2 = st.columns(2)

    with col1:
        pm10 = st.number_input("PM10", 0.0, value=10.0)
        pm25 = st.number_input("PM25", 0.0, value=10.0)
        o3 = st.number_input("O3", 0.0, value=10.0)
        co = st.number_input("CO", 0.0, value=1.0)

    with col2:
        no2 = st.number_input("NO2", 0.0, value=10.0)
        so2 = st.number_input("SO2", 0.0, value=5.0)
        hour = st.slider("Hour of Day", 0, 23, 12)
        location_id = st.number_input("Location ID", 0, value=9187)

    pollutant = st.selectbox(
        "Predict Pollutant",
        ["pm10","pm25","o3","co","no2","so2"]
    )

    if st.button("Predict Air Quality"):

        try:

            response = requests.post(
                "http://backend:8000/predict",
                json={
                    "pm10": pm10,
                    "pm25": pm25,
                    "o3": o3,
                    "co": co,
                    "no2": no2,
                    "so2": so2,
                    "hour": hour,
                    "location_id": location_id,
                    "pollutant": pollutant
                }
            )

            result = response.json()["prediction"]

            st.success(f"Predicted {pollutant.upper()} = {result:.2f}")

            # AQI STATUS
            if result < 50:
                st.info("🟢 Air Quality: Good")

            elif result < 100:
                st.warning("🟡 Air Quality: Moderate")

            else:
                st.error("🔴 Air Quality: Unhealthy")

        except:
            st.error("Backend API is not running. Start backend container.")


# ------------------------------------------------
# FOOTER
# ------------------------------------------------
st.markdown("---")
st.caption("Built with Apache Spark • Hadoop HDFS • FastAPI • Streamlit")