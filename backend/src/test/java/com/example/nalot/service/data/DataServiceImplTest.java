package com.example.nalot.service.data;

import com.example.nalot.dao.LocationDao;
import com.example.nalot.dao.TrendDao;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class DataServiceImplTest {
    @Mock
    TrendDao trendDao;

    @Mock
    LocationDao locationDao;

    @InjectMocks
    DataServiceImpl dataService;

    @Test
    void getWeatherDataset() {
//        //given
//        given(sparkSession.read().format("csv")).willReturn(null);
//
//        //when
//        Dataset<Row> result = dataService.getWeatherDataset();
//
//        //then
//        assertThat(result).isNotNull();
    }

    @Test
    void refineDataSet() {
//       //given
//        Dataset<Row> test = null;
//
//        //when
//        Dataset<Row> result = dataService.refineDataSet(test);
//
//        //then
//        assertThat(result).isNotNull();
    }

    @Test
    void getLocationDataset() {
    }

    @Test
    void getClothesDataset() {
    }

    @Test
    void refineClothesData() {
    }

    @Test
    void getAvgSearch() {
    }

    @Test
    void refineTrainData() {
    }

    @Test
    void makeTrainModel() {
    }

    @Test
    void getPrediction() {
    }

    @Test
    void getAccuracy() {
    }

    @Test
    void addDataSet() {
    }

    @Test
    void calcZscore() {

//        //given
//        double x = 1;
//        double avg = 1;
//        double stddev = 1;
//
//        //when
//        double result = dataService.calcZscore(x, avg, stddev);
//
//        //then
//        assertThat(result).isNotNull();

    }

    @Test
    void getAverage() {
    }

    @Test
    void getStdDev() {
    }

    @Test
    void selectTrendList() {
    }

    @Test
    void makeDataset() {
    }
}