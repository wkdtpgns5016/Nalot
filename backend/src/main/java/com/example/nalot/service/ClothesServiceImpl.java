package com.example.nalot.service;

import com.example.nalot.dao.ClothesDao;
import com.example.nalot.model.ClothesDto;
import com.example.nalot.model.WeatherApiResponse;
import com.example.nalot.model.WeatherDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClothesServiceImpl implements ClothesService {
    private final ClothesDao clothesDao;
    private final WeatherService weatherService;
    public ClothesServiceImpl(ClothesDao clothesDao, WeatherService weatherService){
        this.clothesDao = clothesDao;
        this.weatherService = weatherService;
    }

    @Override
    public List<ClothesDto> selectWeatherListByCategory(String category) {
        return clothesDao.selectWeatherListByCategory(category);
    }

    @Override
    public String sortCategoryByTemperature(WeatherDto weatherDto) {
        String category;
        float temperature = weatherDto.getTemperatureCurrent();

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
}
