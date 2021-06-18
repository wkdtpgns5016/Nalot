package com.example.nalot.model.user;

import com.example.nalot.model.weather.WeatherDto;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserHistoryResponse {
    int id;
    String userId;
    WeatherDto weather;
    UserClothesResponse userClothes;
    Timestamp historyDate;
}
