package com.example.nalot.service;

import com.example.nalot.model.ClothesDto;
import com.example.nalot.model.WeatherDto;
import java.util.List;

public interface ClothesService {
    public List<ClothesDto> selectWeatherListByCategory(String category);
    public String sortCategoryByTemperature(WeatherDto weatherDto);
}
