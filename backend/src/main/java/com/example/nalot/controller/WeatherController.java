package com.example.nalot.controller;

import com.example.nalot.model.weather.*;
import com.example.nalot.service.weather.LocationService;
import com.example.nalot.service.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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

    @GetMapping("/forecasts")
    public List<WeatherResponse> getWeatherList(){
        ArrayList<WeatherResponse> weatherResponses = new ArrayList<>();
        ArrayList<String> dateNow = weatherService.setDateNow();
        List<LocationDto> locationDtos = locationService.selectLocationList();

        for (LocationDto location : locationDtos) {
            WeatherResponse response = new WeatherResponse();
            WeatherApiResponse.Items items = weatherService.getWeatherForecast(
                    dateNow.get(0),
                    dateNow.get(1),
                    location.getGridX(),
                    location.getGridY());
            response.setWeatherDto(weatherService.setWeatherDto(items,dateNow.get(0),dateNow.get(1)));
            response.setLocation(location.getLocationLevel1());
            weatherResponses.add(response);
        }
        return weatherResponses;
    }

    @PostMapping("/forecasts")
    public WeatherDto getWeatherDto(@RequestBody WeatherApiRequest weatherApiRequest){
        ArrayList<String> dateNow = weatherService.setDateNow();
        LocationDto location = locationService.selectLocationByLevel1(weatherApiRequest.getLocation());
        WeatherApiResponse.Items items = weatherService.getWeatherForecast(
                dateNow.get(0),
                dateNow.get(1),
                location.getGridX(),
                location.getGridY());

        return weatherService.setWeatherDto(items,dateNow.get(0),dateNow.get(1));
    }
}
