package com.example.nalot.service.clothes;

import com.example.nalot.dao.ClothesDao;
import com.example.nalot.model.clothes.ClothesDto;
import com.example.nalot.model.data.TrendData;
import com.example.nalot.model.data.TrendDto;
import com.example.nalot.model.weather.WeatherDto;
import com.example.nalot.service.data.DataService;
import com.example.nalot.service.weather.WeatherService;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.desc;

@Service
public class ClothesServiceImpl implements ClothesService {
    private final ClothesDao clothesDao;
    private final DataService dataService;
    public ClothesServiceImpl(ClothesDao clothesDao, DataService dataService){
        this.clothesDao = clothesDao;
        this.dataService = dataService;
    }

    @Override
    public List<ClothesDto> selectClothesListByCategory(String category) {
        return clothesDao.selectClothesListByCategory(category);
    }

    @Override
    public ClothesDto selectClothesInfo(int clothesId) {
        return clothesDao.selectClothesInfo(clothesId);
    }

    @Override
    public String sortCategoryByTemperature(WeatherDto weatherDto) {
        String category;
        float temperature = weatherDto.getTemperature();

        if(temperature <= 4) category = "1";
        else if(temperature >= 5 && temperature <= 8) category = "2";
        else if(temperature >= 9 && temperature <= 11) category = "3";
        else if(temperature >= 12 && temperature <= 16) category = "4";
        else if(temperature >= 17 && temperature <= 19) category = "5";
        else if(temperature >= 20 && temperature <= 22) category = "6";
        else if(temperature >= 23 && temperature <= 27) category = "7";
        else category = "8";

        return category;
    }

    @Override
    public List<ClothesDto> recommendClothes(String date, String location, float temperature) {
        LinearRegressionModel model = dataService.loadLinearRegressionModel();
        Dataset<TrendDto> trend = dataService.addDataSet(date,location,temperature);
        Dataset<Row> prediction = dataService.recommendTrend(model,trend);
        prediction.show();

        List<ClothesDto> list = new ArrayList<>();
        List<Row> clothesList = prediction.select(col("trend").cast("int"), col("prediction"))
                .orderBy(desc("prediction")).limit(5).collectAsList();

        prediction.show();
        for(Row row : clothesList){
            int id = (int)row.get(0) / 10;

            ClothesDto clothesDto = clothesDao.selectClothesInfo(id);
            list.add(clothesDto);
        }

        return list;
    }
}
