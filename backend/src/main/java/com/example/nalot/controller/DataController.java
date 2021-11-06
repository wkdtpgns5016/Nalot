package com.example.nalot.controller;

import com.example.nalot.model.data.TrendData;
import com.example.nalot.service.clothes.ClothesService;
import com.example.nalot.model.data.TrendDto;
import com.example.nalot.service.data.DataService;
import com.example.nalot.service.weather.LocationService;
import org.apache.spark.internal.config.R;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.feature.*;
import org.apache.spark.ml.regression.LinearRegression;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;

import java.io.IOException;
import java.util.List;

import static org.apache.spark.sql.functions.*;


@RestController
@RequestMapping("/data")
public class DataController {

    private final ClothesService clothesService;
    private final DataService dataService;

    public DataController(DataService dataService, ClothesService clothesService) {
        this.dataService = dataService;
        this.clothesService = clothesService;
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

    @GetMapping("/join")
    public Dataset<Row> joinDataSet(){
        Dataset<Row> df = dataService.getWeatherDataset();
        Dataset<Row> temp =  dataService.refineDataSet(df);
        //Dataset<Row> temp = dataService.getLocationDataset(ds);

        Dataset<Row> df2 = dataService.getClothesDataset();
        Dataset<Row> clothes = dataService.refineClothesData(df2);

        Dataset<Row> result = temp.join(clothes,"date");
        return result;
    }


//    @EventListener(ApplicationReadyEvent.class)
//  @GetMapping("/train")
    public Dataset<Row> refineTrainData() throws IOException {

//        Dataset<Row> join = joinDataSet();
//        Dataset<Row> data = dataService.refineTrainData(dataService.refineClothesData(join));
//
//        Dataset<Row> finalData = dataService.extractFeature(data);
//        Dataset<Row> finalData1 = dataService.extractFeatureOne(finalData);
//
//        VectorAssembler vectorAssembler = new VectorAssembler();
//        vectorAssembler.setInputCols(new String[]{"locationVex","clothesVex","seasonVex","month", "zscore"})
//                .setOutputCol("feature");
//
//        Pipeline pipeline =new Pipeline();
//        Dataset<Row> df8 = pipeline.setStages(new PipelineStage[]{(PipelineStage) vectorAssembler}).fit(finalData1).transform(finalData1)
//                .na().fill(0);
//
//        PolynomialExpansion polynomialExpansion = new PolynomialExpansion();
//        polynomialExpansion.setInputCol("feature");
//        polynomialExpansion.setOutputCol("polyFeature");
//        polynomialExpansion.setDegree(5);
//        Dataset<Row> poly = polynomialExpansion.transform(df8);
//        poly.show(false);
//
//        Dataset<Row>[] train = poly.select("search","polyFeature").randomSplit(new double[]{0.9, 0.1});
//        LinearRegressionModel model = dataService.makeTrainModel(train[0]);
//        model.save("backend/src/main/resources/data/model2");

//
//        Dataset<Row> prediction = dataService.getPrediction(model,train[1]);
//
//        prediction.show();
//        RegressionEvaluator evaluator = new RegressionEvaluator();
//        evaluator.setLabelCol("search");
//        evaluator.setPredictionCol("prediction");
//        double i = evaluator.evaluate(prediction);
//
//        System.out.println(i);



//        Dataset<TrendDto> data = dataService.makeDataset();
//        Dataset<Row> result = data.select("*");
//        result.show();
//
//        Dataset<Row> finalData = dataService.extractFeature(result);
//        Dataset<Row> finalData1 = dataService.extractFeatureOne(finalData);
//
//        VectorAssembler vectorAssembler = new VectorAssembler();
//        vectorAssembler.setInputCols(new String[]{"locationVex","clothesVex","seasonVex","month", "zscore"})
//                .setOutputCol("feature");
//
//        Pipeline pipeline =new Pipeline();
//        Dataset<Row> df8 = pipeline.setStages(new PipelineStage[]{(PipelineStage) vectorAssembler}).fit(finalData1).transform(finalData1)
//                .na().fill(0);
//
//        df8.printSchema();

//        dataService.loadDataset("backend/src/main/resources/data/locationVex");



//        Dataset<Row>[] train = df8.select("trend","feature").randomSplit(new double[]{0.9, 0.1});
//        LinearRegressionModel model = dataService.makeTrainModel(train[0]);
//        model.save("backend/src/main/resources/data/model");
//
//        Dataset<Row> prediction = dataService.getPrediction(model,train[1]);
//
//        double pre = dataService.getAccuracy(prediction);
//        System.out.println(pre*100);

//
//       clothesService.recommendClothes("20210106","부산광역시",(float)2.0 );

        return null;
    }

}
