package com.example.nalot.service;

import com.example.nalot.dao.ClothesDao;
import com.example.nalot.model.ClothesDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClothesServiceImpl implements ClothesService {
    private final ClothesDao clothesDao;
    public ClothesServiceImpl(ClothesDao clothesDao){
        this.clothesDao = clothesDao;
    }

    @Override
    public List<ClothesDto> selectWeatherListByCategory(String category) {
        return clothesDao.selectWeatherListByCategory(category);
    }
}
