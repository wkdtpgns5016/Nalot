package com.example.nalot.controller;

import com.example.nalot.model.data.TrendData;
import com.example.nalot.model.data.TrendDto;
import com.example.nalot.service.data.DataService;
import com.example.nalot.service.weather.LocationService;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.classification.LogisticRegressionModel;
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

import java.util.List;

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

        Dataset<Row> processed = dataService.refineTrainData(result);

        LinearRegressionModel model = dataService.makeTrainModel(processed);

        Dataset<TrendData> trend = dataService.addDataSet("20211106","서울특별시",(float) 15.0);
        trend.show();
       //Dataset<Row> prediction = dataService.getPrediction(model,);

//
//        double pre = dataService.getAccuracy(prediction);
//
//        prediction.show();
//        System.out.println(pre*100);
        return null;
    }

}
