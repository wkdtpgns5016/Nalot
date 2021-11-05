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

        Dataset<Row> avg2 = result.groupBy(col("clothes"),col("date")).agg(avg("search").alias("avg2"));

        Dataset<Row> join = result.join(avg,"clothes");

        Dataset<Row> df5 = join.withColumn("search",col("search").cast("double")).filter("search > avg2");

        df5.printSchema();
        Dataset<Row> df52 = df5.withColumn("month",df5.col("date").substr(5,2).cast("int"))
                .select(col("clothes"), col("date"), col("month"), col("location"), col("avg")
                        , col("min") , col("max") , col("search") , col("avg2")
                        ,when(expr("month == 12 OR month == 1 OR month == 2"),"winter")
                                .when(expr("month == 3 OR month == 4 OR month == 5"),"spring")
                                .when(expr("month == 6 OR month == 7 OR month == 8"),"summer")
                                .when(expr("month == 9 OR month == 10 OR month == 11"),"fall").alias("season"));

//        StringIndexer stringIndexer = new StringIndexer();
//        Dataset<Row> df6 = stringIndexer.setInputCol("location")
//                .setOutputCol("locationIndex")
//                .fit(df5).transform(df5);
//
//        Dataset<Row> df67 = stringIndexer.setInputCol("clothes")
//                .setOutputCol("clothesIndex")
//                .fit(df6).transform(df6);
//
//        OneHotEncoder oneHotEncoder = new OneHotEncoder();
//
//        Dataset<Row> df7 = oneHotEncoder.setInputCol("locationIndex")
//                .setOutputCol("locationVex")
//                .fit(df67).transform(df67);
//
//        VectorAssembler vectorAssembler = new VectorAssembler();
//        vectorAssembler.setInputCols(new String[]{"locationVex", "avg"})
//                .setOutputCol("feature");
//
//        Pipeline pipeline =new Pipeline();
//        Dataset<Row> df8 = pipeline.setStages(new PipelineStage[]{(PipelineStage) vectorAssembler}).fit(df7).transform(df7);
//
//        Dataset<Row> finalData = df8.select("clothesIndex","feature");
//        Dataset<Row>[] train = finalData.randomSplit(new double[]{0.8, 0.2});
//
//        LinearRegression linearRegression = new LinearRegression();
//        LinearRegressionModel model =  linearRegression.setMaxIter(10)
//                .setRegParam(0.3)
//                .setLabelCol("clothesIndex")
//                .setFeaturesCol("feature")
//                .fit(train[0]);
//
//        Dataset<Row> prediction = model.transform(train[1]);
//
//        BinaryClassificationEvaluator binaryClassificationEvaluator = new BinaryClassificationEvaluator();
//        double pre = binaryClassificationEvaluator.setRawPredictionCol("prediction")
//                .setLabelCol("clothesIndex")
//                .evaluate(prediction);
//
//        System.out.println(pre*100);
        return null;
    }
}
