package com.example.nalot.service;

import com.example.nalot.model.WeatherApi;
import com.example.nalot.model.WeatherApiResponse;
import com.example.nalot.model.WeatherDto;
import com.example.nalot.model.WeatherForecast;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Value("${weatherApi.url}")
    private String BASE_URL;

    @Value("${weatherApi.serviceKey}")
    private String serviceKey;

    @Override
    public WeatherApiResponse.Items getWeatherForecast(String date, String time, String nx, String ny) {
        try {
            StringBuilder urlBuilder = new StringBuilder(BASE_URL);
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("82", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8"));

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                StringBuilder sb = new StringBuilder();

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                in.lines().forEach(line -> {
                    sb.append(line);
                });
                in.close();
                conn.disconnect();

                return getWeatherApiItems(sb.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public WeatherDto setWeatherDto(WeatherApiResponse.Items items) {
        WeatherDto weather = new WeatherDto();
        for (WeatherForecast item : items.getItem()) {
            if (item.getCategory() == WeatherForecast.CategoryType.T3H) {
                String fcstTime = getFcstTimeByBaseTime(item.getBaseTime());
                if (item.getFcstTime().equals(fcstTime)){
                    weather.setTemperatureCurrent(item.getFcstValue());
                }
            }
            if (item.getCategory() == WeatherForecast.CategoryType.TMN) {
                weather.setTemperatureMin(item.getFcstValue());
            }
            else if (item.getCategory() == WeatherForecast.CategoryType.TMX) {
                weather.setTemperatureMax(item.getFcstValue());
            }
        }
        return weather;
    }

    private WeatherApiResponse.Items getWeatherApiItems(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherApi data = objectMapper.readValue(json, WeatherApi.class);
            return data.getResponse().getBody().getItems();

        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getFcstTimeByBaseTime(String baseTime){
        int base = Integer.parseInt(baseTime);
        int fcst = (base + 400) % 2400;
        if(fcst < 100) return "0000";
        else if(fcst >= 100 && fcst < 1000) return '0' + Integer.toString(fcst);
        else if(fcst >= 1000 && fcst <= 2300) return Integer.toString(fcst);
        return null;
    }
}
