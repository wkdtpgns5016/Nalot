package com.example.nalot.service.weather;

import com.example.nalot.model.weather.WeatherApiResponse;
import com.example.nalot.model.weather.WeatherDto;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;

public interface WeatherService {
    public ArrayList<String> setDateNow();
    public WeatherApiResponse.Items getWeatherForecast(String date, String time, String nx, String ny);
    public WeatherDto setWeatherDto(WeatherApiResponse.Items items, String date, String time);
    public WeatherDto selectWeatherInfo(String weatherId);
    public int insertWeatherInfo(WeatherDto weatherDto);
    public int deleteWeatherInfo(String weatherId);
    public int deleteWeatherListByUserId(String userId);
    public Dataset<Row> getWeatherDataset();
    public Dataset<Row> getLocationDataset(Dataset<Row> ds);
}
