package com.example.nalot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDto {
    float TemperatureMin;
    float TemperatureMax;
    float TemperatureCurrent;
}
