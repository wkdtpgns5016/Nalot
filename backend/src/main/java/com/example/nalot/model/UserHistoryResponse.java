package com.example.nalot.model;

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
