package com.example.nalot.dao;

import com.example.nalot.model.weather.LocationDto;
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
@ContextConfiguration(classes = LocationDao.class)
class LocationDaoTest {

    @Autowired
    LocationDao locationDao;

    @Test
    void selectLocationList() {
        //given

        //when
        List<LocationDto> result = locationDao.selectLocationList();

        //then
        assertThat(result.size()).isEqualTo(18);
    }

    @Test
    void selectLocationByLevel1() {
        //given
        String level = "서울특별시";

        //when
        LocationDto result = locationDao.selectLocationByLevel1(level);

        //then
        assertThat(result.getLocationLevel1()).isEqualTo(level);
    }
}