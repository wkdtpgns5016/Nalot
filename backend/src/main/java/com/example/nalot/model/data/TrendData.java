package com.example.nalot.model.data;

import lombok.Data;

@Data
public class TrendData {
    String clothes;
    int month;
    String location;
    double value;
    double trend;
    String season;
    double zScore;
}
