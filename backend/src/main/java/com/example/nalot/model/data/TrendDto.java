package com.example.nalot.model.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TrendDto {
    String clothes;
    String date;
    int month;
    String location;
    double value;
    int search;
    int trend;
    String season;
    double zscore;
}
