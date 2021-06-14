package com.example.nalot.service;

import com.example.nalot.model.WeatherApiResponse;
import com.example.nalot.model.WeatherDto;

public interface WeatherService {
    public WeatherApiResponse.Items getWeatherForecast(String date, String time, String nx, String ny);
    public WeatherDto setWeatherDto(WeatherApiResponse.Items items);
    public WeatherDto selectWeatherInfo(int weatherId);
    public int insertWeatherInfo(WeatherDto weatherDto);
    public int deleteWeatherInfo(int weatherId);
}
