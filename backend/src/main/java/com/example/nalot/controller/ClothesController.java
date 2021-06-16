package com.example.nalot.controller;

import com.example.nalot.model.ClothesDto;
import com.example.nalot.model.WeatherDto;
import com.example.nalot.service.ClothesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/clothes")
public class ClothesController {
    private final ClothesService clothesService;
    public ClothesController(ClothesService clothesService){
        this.clothesService = clothesService;
    }

    @PostMapping("/recommendations")
    public List<ClothesDto> recommendClothesByTemperature(@RequestBody WeatherDto weatherDto){
        String category = clothesService.sortCategoryByTemperature(weatherDto);
        return clothesService.selectWeatherListByCategory(category);
    }
}
