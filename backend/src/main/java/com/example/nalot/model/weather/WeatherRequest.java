package com.example.nalot.model.weather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherRequest {
    String date;
    String location;
    float temperature;
}
