package com.example.nalot.dao;

import com.example.nalot.model.clothes.ClothesDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = ClothesDao.class)
class ClothesDaoTest {

    @Autowired
    ClothesDao clothesDao;

    @Test
    void selectClothesListByCategory() {
        //given
        String category = "1";

        //when
        List<ClothesDto> result = clothesDao.selectClothesListByCategory(category);

        //then
        assertThat(result.size()).isEqualTo(5);
    }

    @Test
    void selectClothesInfo() {
        //given
        int clothesId = 1;

        //when
        ClothesDto result = clothesDao.selectClothesInfo(clothesId);

        //then
        assertThat(result.getId()).isEqualTo(clothesId);
    }
}