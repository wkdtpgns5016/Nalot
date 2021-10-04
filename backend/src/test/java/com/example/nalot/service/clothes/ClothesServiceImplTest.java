package com.example.nalot.service.clothes;

import com.example.nalot.dao.ClothesDao;
import com.example.nalot.model.clothes.ClothesDto;
import com.example.nalot.model.weather.WeatherDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class ClothesServiceImplTest {
    @InjectMocks
    ClothesServiceImpl clothesService;

    @Mock
    ClothesDao clothesDao;

    @Test
    void selectClothesListByCategory() {
        //given
        String category = "1";
        ClothesDto clothes = new ClothesDto();
        clothes.setId(0);
        clothes.setName("티셔츠");

        ClothesDto clothes1 = new ClothesDto();
        clothes1.setId(1);
        clothes1.setName("반바지");

        List<ClothesDto> list = new ArrayList<>();
        list.add(clothes);
        list.add(clothes1);

        given(clothesDao.selectClothesListByCategory(category)).willReturn(list);

        //when
        List<ClothesDto> result = clothesService.selectClothesListByCategory(category);

        //then
        assertThat(result.get(1).getName()).isEqualTo("반바지");
    }

    @Test
    void selectClothesInfo() {
        //given
        int clothesId = 0;
        ClothesDto clothesDto = new ClothesDto();
        clothesDto.setId(clothesId);
        clothesDto.setName("티셔츠");

        given(clothesDao.selectClothesInfo(clothesId)).willReturn(clothesDto);

        //when
        ClothesDto result = clothesService.selectClothesInfo(clothesId);

        //then
        assertThat(result.getName()).isEqualTo("티셔츠");
    }

    @Test
    void sortCategoryByTemperature() {
        //given
        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setId("test");
        weatherDto.setUserId("userId");
        weatherDto.setTemperature(2);

        //when
        String result = clothesService.sortCategoryByTemperature(weatherDto);

        //then
        assertThat(result).isEqualTo("1");
    }
}