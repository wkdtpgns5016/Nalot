package com.example.nalot.controller;

import com.example.nalot.model.clothes.ClothesDto;
import com.example.nalot.model.weather.WeatherDto;
import com.example.nalot.service.clothes.ClothesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clothes")
public class ClothesController {
    private final ClothesService clothesService;
    public ClothesController(ClothesService clothesService){
        this.clothesService = clothesService;
    }

    @PostMapping("/recommendations")
    public List<ClothesDto> recommendClothesByTemperature(@RequestBody WeatherDto weatherDto){
        String category = clothesService.sortCategoryByTemperature(weatherDto);
        return clothesService.selectClothesListByCategory(category);
    }
}
