package com.example.nalot.model.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDto {
    String id;
    float temperature;
    String userId;
    String baseDate;
    String baseTime;
}
