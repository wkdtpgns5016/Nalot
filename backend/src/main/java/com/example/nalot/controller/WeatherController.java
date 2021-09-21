package com.example.nalot.controller;

import com.example.nalot.model.weather.LocationDto;
import com.example.nalot.model.weather.WeatherApiRequest;
import com.example.nalot.model.weather.WeatherApiResponse;
import com.example.nalot.model.weather.WeatherDto;
import com.example.nalot.service.weather.LocationService;
import com.example.nalot.service.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/weathers")
public class WeatherController {
    private final WeatherService weatherService;
    private final LocationService locationService;

    public WeatherController(WeatherService weatherService, LocationService locationService) {
        this.weatherService = weatherService;
        this.locationService = locationService;
    }

    @PostMapping("")
    public WeatherApiResponse.Items getWeatherForecast(@RequestBody WeatherApiRequest weatherApiRequest){
        ArrayList<String> dateNow = weatherService.setDateNow();
        LocationDto grid = locationService.selectLocationByLevel1(weatherApiRequest.getLocation());
        return weatherService.getWeatherForecast(
                dateNow.get(0),
                dateNow.get(1),
                grid.getGridX(),
                grid.getGridY());
    }

    @PostMapping("/forecasts")
    public WeatherDto getWeatherDto(@RequestBody WeatherApiRequest weatherApiRequest){
        ArrayList<String> dateNow = weatherService.setDateNow();
        LocationDto grid = locationService.selectLocationByLevel1(weatherApiRequest.getLocation());
        WeatherApiResponse.Items items = weatherService.getWeatherForecast(
                dateNow.get(0),
                dateNow.get(1),
                grid.getGridX(),
                grid.getGridY());

        return weatherService.setWeatherDto(items,dateNow.get(0),dateNow.get(1));
    }
}
