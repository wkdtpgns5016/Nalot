package com.example.nalot.service.weather;

import com.example.nalot.model.weather.WeatherApiResponse;
import com.example.nalot.model.weather.WeatherDto;

public interface WeatherService {
    public WeatherApiResponse.Items getWeatherForecast(String date, String time, String nx, String ny);
    public WeatherDto setWeatherDto(WeatherApiResponse.Items items, String date, String time);
    public WeatherDto selectWeatherInfo(String weatherId);
    public int insertWeatherInfo(WeatherDto weatherDto);
    public int deleteWeatherInfo(String weatherId);
    public int deleteWeatherListByUserId(String userId);
}
