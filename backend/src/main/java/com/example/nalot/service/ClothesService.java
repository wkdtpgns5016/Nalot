package com.example.nalot.service;

import com.example.nalot.model.ClothesDto;
import java.util.List;

public interface ClothesService {
    public List<ClothesDto> selectWeatherListByCategory(String category);
}
