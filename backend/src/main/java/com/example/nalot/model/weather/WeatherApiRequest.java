package com.example.nalot.model.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherApiRequest {
    String date;
    String time;
    String nx;
    String ny;
}
