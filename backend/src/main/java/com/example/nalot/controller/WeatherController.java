package com.example.nalot.controller;

import com.example.nalot.model.weather.WeatherApiRequest;
import com.example.nalot.model.weather.WeatherApiResponse;
import com.example.nalot.model.weather.WeatherDto;
import com.example.nalot.service.weather.WeatherService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/weathers")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("")
    public WeatherApiResponse.Items getWeatherForecast(@RequestBody WeatherApiRequest weatherApiRequest){
        ArrayList<String> dateNow = weatherService.setDateNow();
        return weatherService.getWeatherForecast(
                dateNow.get(0),
                dateNow.get(1),
                weatherApiRequest.getNx(),
                weatherApiRequest.getNy());
    }

    @PostMapping("/forecasts")
    public WeatherDto getWeatherDto(@RequestBody WeatherApiRequest weatherApiRequest){
        ArrayList<String> dateNow = weatherService.setDateNow();
        WeatherApiResponse.Items items = weatherService.getWeatherForecast(
                dateNow.get(0),
                dateNow.get(1),
                weatherApiRequest.getNx(),
                weatherApiRequest.getNy());

        return weatherService.setWeatherDto(items,dateNow.get(0),dateNow.get(1));
    }
}
