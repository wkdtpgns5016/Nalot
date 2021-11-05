package com.example.nalot.controller;

import com.example.nalot.service.data.DataService;
import com.example.nalot.service.weather.LocationService;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator;
import org.apache.spark.ml.feature.OneHotEncoderModel;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.regression.LinearRegression;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.ml.feature.OneHotEncoder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;

import static org.apache.spark.sql.functions.*;


@RestController
@RequestMapping("/data")
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    //@EventListener(ApplicationReadyEvent.class)
    @GetMapping("/data")
    public Dataset<Row> getWeatherDataset() {
        Dataset<Row> df = dataService.getWeatherDataset();
        df.printSchema();
        return df;
    }

    //@EventListener(ApplicationReadyEvent.class)
    @GetMapping("/refine")
    public Dataset<Row> refineWeatherDataSet() {
        Dataset<Row> df = dataService.getWeatherDataset();
        return dataService.refineDataSet(df);

    }


    public Dataset<Row> joinDataSet(){
        Dataset<Row> df = dataService.getWeatherDataset();
        Dataset<Row> temp =  dataService.refineDataSet(df);
        //Dataset<Row> temp = dataService.getLocationDataset(ds);

        Dataset<Row> df2 = dataService.getClothesDataset();
        Dataset<Row> clothes = dataService.refineClothesData(df2);

        Dataset<Row> result = temp.join(clothes,"date");
//-------------------------------------------------------------------------------


        return result;
    }

    @EventListener(ApplicationReadyEvent.class)
    public Dataset<Row> refineTrainData() {
        Dataset<Row> result = this.joinDataSet();

        Dataset<Row> avg = result.groupBy(col("clothes")).agg(avg("search").alias("avg2"));

        Dataset<Row> join = result.join(avg,"clothes");
        Dataset<Row> df5 = join.withColumn("search",col("search").cast("double")).filter("search > avg2");

        Dataset<Row> midhigh = df5.groupBy(col("clothes")).agg(avg("search").alias("midhigh"));

        Dataset<Row> df57 = join.withColumn("search",col("search").cast("double")).filter("search < avg2");

        Dataset<Row> midlow = df57.groupBy(col("clothes")).agg(avg("search").alias("midlow"));

        Dataset<Row> df58 = join.join(midhigh,"clothes").join(midlow,"clothes");


        Dataset<Row> trend = df58.select(col("clothes"),col("date"),col("location"),
                                col("avg"), col("search"),
                                when(expr("search > midhigh"),3)
                                .when(expr("search > avg2 AND search <= midhigh"),2)
                                .when(expr("search > midlow  AND search <= avg2"),1)
                                .when(expr("search < midlow"),0).cast("double").alias("trend"));


        Dataset<Row> df52 = trend.withColumn("month",df5.col("date").substr(5,2).cast("int"))
                .select(col("clothes"), col("date"), col("month"), col("location"), col("avg")
                        , col("search") , col("trend")
                        ,when(expr("month == 12 OR month == 1 OR month == 2"),"winter")
                                .when(expr("month == 3 OR month == 4 OR month == 5"),"spring")
                                .when(expr("month == 6 OR month == 7 OR month == 8"),"summer")
                                .when(expr("month == 9 OR month == 10 OR month == 11"),"fall").alias("season"));
        //x-평균 / 표준편차
        //평균 -> 일평균의 평균
        Dataset<Row> dayavgavg = df52.select(avg(col("avg")));

        //dayavgavg.show();

        //표준편차 공식 -> 일평균의 표준편차
        Dataset<Row> stddev = df52.select(stddev(col("avg")));

        //stddev.show();

        //x-평균 / 표준편차 적용 -> Z정규화

        //column 생성
        Dataset<Row> zscore = df52.withColumn("dayavgavg", lit(dayavgavg.collectAsList().get(0).get(0)))
                                .withColumn("stddev", lit(stddev.collectAsList().get(0).get(0)))
                                .withColumn("zscore", expr("(avg-dayavgavg)/stddev"));
        //zscore.show();

        Dataset<Row> processed = zscore.withColumnRenamed("avg", "value")
                .drop("dayavgavg", "stddev");


        StringIndexer stringIndexer = new StringIndexer();
        Dataset<Row> df6 = stringIndexer.setInputCol("location")
                .setOutputCol("locationIndex")
                .fit(processed).transform(processed);

        Dataset<Row> df67 = stringIndexer.setInputCol("clothes")
                .setOutputCol("clothesIndex")
                .fit(df6).transform(df6);

        Dataset<Row> df68 = stringIndexer.setInputCol("season")
                .setOutputCol("seasonIndex")
                .fit(df67).transform(df67);


        OneHotEncoder oneHotEncoder = new OneHotEncoder();

        Dataset<Row> df7 = oneHotEncoder.setInputCol("locationIndex")
                .setOutputCol("locationVex")
                .fit(df68).transform(df68);

        Dataset<Row> df77 = oneHotEncoder.setInputCol("clothesIndex")
                .setOutputCol("clothesVex")
                .fit(df7).transform(df7);

        Dataset<Row> df78 = oneHotEncoder.setInputCol("seasonIndex")
                .setOutputCol("seasonVex")
                .fit(df77).transform(df77);

        VectorAssembler vectorAssembler = new VectorAssembler();
        vectorAssembler.setInputCols(new String[]{"locationVex","clothesVex","seasonVex", "zscore"})
                .setOutputCol("feature");



        Pipeline pipeline =new Pipeline();
        Dataset<Row> df8 = pipeline.setStages(new PipelineStage[]{(PipelineStage) vectorAssembler}).fit(df78).transform(df78);

        Dataset<Row> finalData = df8.select("trend","feature");

//        finalData.show(false);
        Dataset<Row>[] train = finalData.randomSplit(new double[]{0.7, 0.3});


        LinearRegression linearRegression = new LinearRegression();
        LinearRegressionModel model =  linearRegression.setMaxIter(10)
                .setRegParam(0.3)
                .setLabelCol("trend")
                .setFeaturesCol("feature")
                .fit(train[0]);

        Dataset<Row> prediction = model.transform(train[1])
            .withColumn("prediction",bround(col("prediction")));

        prediction.show();
//
//
//        BinaryClassificationEvaluator binaryClassificationEvaluator = new BinaryClassificationEvaluator();
//        double pre = binaryClassificationEvaluator.setRawPredictionCol("prediction")
//                .setLabelCol("trend")
//                .evaluate(prediction);
//
//        System.out.println(pre*100);
        return null;
    }
}
