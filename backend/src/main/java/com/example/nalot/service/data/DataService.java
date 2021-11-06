package com.example.nalot.service.data;

import com.example.nalot.model.data.TrendData;
import com.example.nalot.model.data.TrendDto;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;

public interface DataService {
    public Dataset<Row> getWeatherDataset();
    public Dataset<Row> refineDataSet(Dataset<Row> df);
    public Dataset<Row> getLocationDataset(Dataset<Row> ds);
    public Dataset<Row> getClothesDataset();
    public Dataset<Row> refineClothesData(Dataset<Row> df);
    public Dataset<Row> refineTrainData(Dataset<Row> result);
    public LinearRegressionModel makeTrainModel(Dataset<Row> train);
    public Dataset<Row> extractFeature(Dataset<Row> dataset);
    public Dataset<TrendData> addDataSet(String date, String location, float temperature );
    public Dataset<Row> getPrediction(LinearRegressionModel model, Dataset<Row> dataset);
    public double getAccuracy(Dataset<Row> prediction);
    public double getAverage();
    public double getStdDev();
    public Dataset<Row> recommendTrend(LinearRegressionModel model, Dataset<TrendData> trend);
    LinearRegressionModel loadLinearRegressionModel();
    public List<TrendDto> selectTrendList();
    public Dataset<TrendDto> makeDataset();

}
