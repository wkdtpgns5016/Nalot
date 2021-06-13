package com.example.nalot.service;

import com.example.nalot.model.WeatherApi;
import com.example.nalot.model.WeatherApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final String BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst";
    private final String serviceKey = "UjMjQb3kWN4PqhujkHx6%2FUj65fmqEp0sqHviiS1Ddw63JE9EJCxC4%2B4TIWr4PcWKyRm6vZUlVjfmc0aSGm%2BvYg%3D%3D"; /*공공데이터포털에서 받은 인증키*/

    @Override
    public WeatherApiResponse.Items getWeatherForecast(String date, String time, String nx, String ny) {
        try {
            StringBuilder urlBuilder = new StringBuilder(BASE_URL);
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("62", "UTF-8"));
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
}
