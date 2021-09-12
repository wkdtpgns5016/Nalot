package com.example.nalot.service;

import com.example.nalot.NalotApplication;
import com.example.nalot.model.weather.WeatherApiResponse;
import com.example.nalot.model.weather.WeatherDto;
import com.example.nalot.model.weather.WeatherForecast;
import com.example.nalot.service.weather.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NalotApplication.class)
public class WeatherServiceTest {
    @Autowired
    WeatherService weatherService;

    @Test
    public void setDateNowTest(){
        //given
        ArrayList<String> dateNow = new ArrayList<>();

        //when
        dateNow = weatherService.setDateNow();

        //then
        System.out.println(dateNow.get(0));
        System.out.println(dateNow.get(1));

        assertThat(dateNow.size()).isNotZero();
    }

    @Test
    public void getWeatherForecastTest(){
        //given
        ArrayList<String> dateNow = new ArrayList<>();
        dateNow = weatherService.setDateNow();

        String date = dateNow.get(0);
        String time = dateNow.get(1);
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
        ArrayList<String> dateNow = new ArrayList<>();
        dateNow = weatherService.setDateNow();

        String date = dateNow.get(0);
        String time = dateNow.get(1);
        String nx = "60";
        String ny = "127";
        WeatherApiResponse.Items items = weatherService.getWeatherForecast(date, time, nx, ny);

        //when
        WeatherDto weatherDto = weatherService.setWeatherDto(items, date, time);

        //then
        assertThat(weatherDto).isNotNull();

    }

    @Test
    public void selectWeatherInfoTest(){
        //given
        String weatherId = "1";
        float temperature = 24.0F;

        //when
        WeatherDto weatherDto = weatherService.selectWeatherInfo(weatherId);

        //then
        assertThat(weatherDto.getTemperature()).isEqualTo(temperature);

    }

    @Test
    public void insertWeatherInfoTest(){
        //given
        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setTemperature(24F);
        weatherDto.setBaseDate("20210618");
        weatherDto.setBaseTime("0500");

        //when
        int result = weatherService.insertWeatherInfo(weatherDto);

        //then
        assertThat(result).isOne();

    }

    @Test
    public void deleteWeatherInfoTest(){
        //given
        String weatherId = "1";

        //when
        int result = weatherService.deleteWeatherInfo(weatherId);

        //then
        assertThat(result).isOne();

    }
}
