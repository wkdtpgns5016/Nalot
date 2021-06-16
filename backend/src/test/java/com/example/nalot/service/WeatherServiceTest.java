package com.example.nalot.service;

import com.example.nalot.NalotApplication;
import com.example.nalot.model.WeatherApiResponse;
import com.example.nalot.model.WeatherDto;
import com.example.nalot.model.WeatherForecast;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NalotApplication.class)
public class WeatherServiceTest {
    @Autowired
    WeatherService weatherService;

    @Test
    public void getWeatherForecastTest(){
        //given
        String date = "20210614";
        String time = "0500";
        String nx = "60";
        String ny = "127";

        //when
        WeatherApiResponse.Items items = weatherService.getWeatherForecast(date, time, nx, ny);
        List<WeatherForecast> list = items.getItem();

        //then
        assertThat(list.size()).isNotZero();
    }

    @Test
    public void setWeatherDtoTest(){
        //given
        String date = "20210614";
        String time = "0500";
        String nx = "60";
        String ny = "127";
        WeatherApiResponse.Items items = weatherService.getWeatherForecast(date, time, nx, ny);

        //when
        WeatherDto weatherDto = weatherService.setWeatherDto(items);

        //then
        assertThat(weatherDto).isNotNull();

    }

    @Test
    public void selectWeatherInfoTest(){
        //given
        int weatherId = 1;
        float temperatureMin = 24.0F;

        //when
        WeatherDto weatherDto = weatherService.selectWeatherInfo(weatherId);

        //then
        assertThat(weatherDto.getTemperatureMin()).isEqualTo(temperatureMin);

    }

    @Test
    public void insertWeatherInfoTest(){
        //given
        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setTemperatureMin(24F);
        weatherDto.setTemperatureMax(28.2F);
        weatherDto.setTemperatureCurrent(25.1F);

        //when
        int result = weatherService.insertWeatherInfo(weatherDto);

        //then
        assertThat(result).isOne();

    }

    @Test
    public void deleteWeatherInfoTest(){
        //given
        int weatherId = 1;

        //when
        int result = weatherService.deleteWeatherInfo(weatherId);

        //then
        assertThat(result).isOne();

    }
}