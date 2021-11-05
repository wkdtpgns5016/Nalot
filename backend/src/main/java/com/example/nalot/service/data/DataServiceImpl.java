package com.example.nalot.service.data;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import static org.apache.spark.sql.functions.*;


@Service
public class DataServiceImpl implements DataService{


    SparkSession spark = SparkSession
            .builder()
            .appName("Java Spark SQL basic example")
            .config("spark.some.config.option", "some-value")
            .getOrCreate();

    @Override
    public Dataset<Row> getWeatherDataset() {
        Dataset<Row> peopleDFCsv = spark.read().format("csv")
                .option("sep", ",")
                .option("inferSchema", "true")
                .option("header", "true")
                .load("backend/src/main/resources/data/temperature1.csv");

        return peopleDFCsv;
    }


    @Override
    public Dataset<Row> refineDataSet(Dataset<Row> df) {

        Dataset<Row> df2 = df.withColumn(" format: day",col(" format: day").cast("int"))
                .withColumnRenamed(" format: day","day")
                .withColumnRenamed("value location:91_78 Start : 20170801 ","value")
                .withColumn("month",expr("month*100+day"))
                .withColumnRenamed("month","date")
                .drop("day")
                .groupBy("date", "location").agg(avg("value").alias("avg"),
                min("value").alias("min"), max("value").alias("max"));

        return df2;
    }

    @Override
    public Dataset<Row> getLocationDataset(Dataset<Row> ds){

        Dataset<Row> avg = ds.groupBy("date", "location").agg(avg("value").alias("avg"),
                min("value").alias("min"), max("value").alias("max"));


        return avg;
    }

    @Override
    public Dataset<Row> getClothesDataset() {
        Dataset<Row> peopleDFCsv = spark.read().format("csv")
                .option("sep", ",")
                .option("inferSchema", "true")
                .option("header", "true")
                .load("backend/src/main/resources/data/clothes.csv");

        return peopleDFCsv;
    }

    @Override
    public Dataset<Row> refineClothesData(Dataset<Row> df) {
        Dataset<Row> df2 = df.withColumnRenamed("날짜","date")
                .withColumnRenamed("검색횟수","search")
                .withColumnRenamed("옷종류","clothes")
                .withColumn("date", expr("regexp_replace(date,\"-\",\"\")"))
                .withColumn("date",col("date").cast("int"));
        return df2;
    }


    public Dataset<Row> getAvgSearch(Dataset<Row> a) {
        Dataset<Row> df = a.groupBy(col("clothes")).avg("search");
        df.show();
        return null;
    }

    public Dataset<Row> refineTrainData(Dataset<Row> result) {

        return null;
    }

}
