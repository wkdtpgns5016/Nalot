package com.example.nalot.service.clothes;

import com.example.nalot.model.clothes.ClothesDto;
import com.example.nalot.model.data.TrendData;
import com.example.nalot.model.weather.WeatherDto;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;

public interface ClothesService {
    public List<ClothesDto> selectClothesListByCategory(String category);
    public ClothesDto selectClothesInfo(int clothesId);
    public String sortCategoryByTemperature(WeatherDto weatherDto);
    public List<ClothesDto> recommendClothes(String date, String location, float temperature);
}
