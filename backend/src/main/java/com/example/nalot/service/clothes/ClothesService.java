package com.example.nalot.service.clothes;

import com.example.nalot.model.clothes.ClothesDto;
import com.example.nalot.model.weather.WeatherDto;
import java.util.List;

public interface ClothesService {
    public List<ClothesDto> selectClothesListByCategory(String category);
    public ClothesDto selectClothesInfo(int clothesId);
    public String sortCategoryByTemperature(WeatherDto weatherDto);
}
