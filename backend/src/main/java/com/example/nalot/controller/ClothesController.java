package com.example.nalot.controller;

import com.example.nalot.model.clothes.ClothesDto;
import com.example.nalot.model.data.TrendData;
import com.example.nalot.model.data.TrendDto;
import com.example.nalot.model.weather.WeatherDto;
import com.example.nalot.model.weather.WeatherRequest;
import com.example.nalot.service.clothes.ClothesService;
import com.example.nalot.service.data.DataService;
import org.apache.spark.sql.Dataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clothes")
public class ClothesController {
    private final DataService dataService;
    private final ClothesService clothesService;
    public ClothesController(ClothesService clothesService, DataService dataService){
        this.clothesService = clothesService;
        this.dataService = dataService;
    }

    @PostMapping("/recommendations")
    public List<ClothesDto> recommendClothesByTemperature(@RequestBody WeatherDto weatherDto){
        String category = clothesService.sortCategoryByTemperature(weatherDto);
        return clothesService.selectClothesListByCategory(category);
    }

    @PostMapping("/recommendations2")
    public List<ClothesDto> recommendClothes(@RequestBody WeatherRequest weatherRequest){
        List<ClothesDto> list = new ArrayList<>();
        ClothesDto clothesDto = new ClothesDto();
        clothesDto.setName("a");
        clothesDto.setId(0);
        clothesDto.setCategory("1");
        list.add(clothesDto);

        return list;
    }
}
