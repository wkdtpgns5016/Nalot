package com.example.nalot.controller;

import com.example.nalot.service.data.DataService;
import com.example.nalot.service.weather.LocationService;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

    @EventListener(ApplicationReadyEvent.class)
    public Dataset<Row> joinDataSet(){
        Dataset<Row> df = dataService.getWeatherDataset();
        Dataset<Row> ds =  dataService.refineDataSet(df);
        Dataset<Row> temp = dataService.getLocationDataset(ds);
        //temp.sort(ds.col("date").desc()).show();

        Dataset<Row> df2 = dataService.getClothesDataset();
        Dataset<Row> clothes = dataService.refineClothesData(df2);

        temp.show();
        clothes.show();
        Dataset<Row> result = dataService.joinDataSet(temp,clothes);

        return result;
    }
}
