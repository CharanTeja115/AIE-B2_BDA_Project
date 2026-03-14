FROM python:3.10-slim

WORKDIR /app

COPY dashboard.py /app

RUN pip install streamlit requests plotly pandas

CMD ["streamlit","run","dashboard.py","--server.port=8501","--server.address=0.0.0.0"]