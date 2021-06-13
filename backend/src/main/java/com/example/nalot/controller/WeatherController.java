package com.example.nalot.controller;

import com.example.nalot.model.WeatherApiRequest;
import com.example.nalot.model.WeatherApiResponse;
import com.example.nalot.service.WeatherService;
import org.springframework.web.bind.annotation.*;


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
        return weatherService.getWeatherForecast(
                weatherApiRequest.getDate(),
                weatherApiRequest.getTime(),
                weatherApiRequest.getNx(),
                weatherApiRequest.getNy());
    }
}
