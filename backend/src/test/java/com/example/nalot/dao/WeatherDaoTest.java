package com.example.nalot.dao;

import com.example.nalot.model.weather.WeatherDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = WeatherDao.class)
class WeatherDaoTest {

    @Autowired
    WeatherDao weatherDao;

    @Test
    void selectWeatherInfo() {
        //given
        String weatherId = "20210617152956test";

        //when
        WeatherDto result = weatherDao.selectWeatherInfo(weatherId);

        //then
        assertThat(result.getId()).isEqualTo(weatherId);
    }

    @Test
    void insertWeatherInfo() {
        //given
        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setId("test");
        weatherDto.setTemperature(23);
        weatherDto.setBaseTime("2000");
        weatherDto.setBaseDate("20211005");
        weatherDto.setUserId("test");

        //when
        int result = weatherDao.insertWeatherInfo(weatherDto);

        //then
        assertThat(result).isOne();
    }

    @Test
    void deleteWeatherInfo() {
        //given
        String weatherId = "20210617152956test";

        //when
        int result = weatherDao.deleteWeatherInfo(weatherId);

        //then
        assertThat(result).isOne();
    }

    @Test
    void deleteWeatherListByUserId() {
        //given
        String userId = "test";

        //when
        int result = weatherDao.deleteWeatherListByUserId(userId);

        //then
        assertThat(result).isZero();
    }
}