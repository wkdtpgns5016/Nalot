package com.example.nalot.service;

import com.example.nalot.model.WeatherApiResponse;

public interface WeatherService {
    public WeatherApiResponse.Items getWeatherForecast(String date, String time, String nx, String ny);
}
