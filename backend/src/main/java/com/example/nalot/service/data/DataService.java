package com.example.nalot.service.data;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface DataService {
    public Dataset<Row> getWeatherDataset();
    public Dataset<Row> refineDataSet(Dataset<Row> df);
    public Dataset<Row> getLocationDataset(Dataset<Row> ds);
    public Dataset<Row> getClothesDataset();
    public Dataset<Row> refineClothesData(Dataset<Row> df);

}
