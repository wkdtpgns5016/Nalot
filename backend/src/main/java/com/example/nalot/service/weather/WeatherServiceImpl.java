package com.example.nalot.service.weather;

import com.example.nalot.dao.LocationDao;
import com.example.nalot.dao.WeatherDao;
import com.example.nalot.model.weather.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

// $example on:schema_merging$
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// $example off:schema_merging$
import java.util.Properties;

// $example on:basic_parquet_example$
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Encoders;
// $example on:schema_merging$
// $example on:json_dataset$
// $example on:csv_dataset$
// $example on:text_dataset$
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
// $example off:text_dataset$
// $example off:csv_dataset$
// $example off:json_dataset$
// $example off:schema_merging$
// $example off:basic_parquet_example$
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.*;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Value("${weatherApi.url}")
    private String BASE_URL;

    @Value("${weatherApi.serviceKey}")
    private String serviceKey;

    private final WeatherDao weatherDao;
    private final LocationDao locationDao;
    public WeatherServiceImpl(WeatherDao weatherDao, LocationDao locationDao) { this.weatherDao = weatherDao;
        this.locationDao = locationDao;
    }

    SparkSession spark = SparkSession
            .builder()
            .appName("Java Spark SQL basic example")
            .config("spark.some.config.option", "some-value")
            .getOrCreate();

    SparkSession spark = SparkSession
            .builder()
            .appName("Java Spark SQL basic example")
            .config("spark.some.config.option", "some-value")
            .getOrCreate();

    @Override
    public ArrayList<String> setDateNow() {
        ArrayList<String> dateNow = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);

        TimeZone timeZone = TimeZone.getTimeZone("Asia/Seoul");
        dateFormat.setTimeZone(timeZone);
        hourFormat.setTimeZone(timeZone);
        minuteFormat.setTimeZone(timeZone);

        int minute = Integer.parseInt(minuteFormat.format(now));
        if (minute < 30) {
            cal.add(Calendar.HOUR_OF_DAY,-1);
        }
        dateNow.add(dateFormat.format(cal.getTime()));
        dateNow.add(hourFormat.format(cal.getTime())+"00");

        return dateNow;
    }

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
    public WeatherDto setWeatherDto(WeatherApiResponse.Items items, String date, String time) {
        WeatherDto weather = new WeatherDto();
        weather.setBaseDate(date);
        weather.setBaseTime(time);
        for (WeatherForecast item : items.getItem()) {
            if (item.getCategory() == WeatherForecast.CategoryType.T1H) {
                weather.setTemperature(item.getObsrValue());
            }
        }
        return weather;
    }

    @Override
    public WeatherDto selectWeatherInfo(String weatherId) {
        return weatherDao.selectWeatherInfo(weatherId);
    }

    @Override
    public int insertWeatherInfo(WeatherDto weatherDto) {
        return weatherDao.insertWeatherInfo(weatherDto);
    }

    @Override
    public int deleteWeatherInfo(String weatherId) {
        return weatherDao.deleteWeatherInfo(weatherId);
    }

    @Override
    public int deleteWeatherListByUserId(String userId) {
        return weatherDao.deleteWeatherListByUserId(userId);
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

    @Override
    public Dataset<Row> getWeatherDataset() {
        Dataset<Row> peopleDFCsv = spark.read().format("csv")
                .option("sep", ",")
                .option("inferSchema", "true")
                .option("header", "true")
                .load("backend/src/main/resources/data/temperature1.csv");

        return peopleDFCsv;
    }


    @Override
    public Dataset<Row> refineDataSet(Dataset<Row> df) {

        Dataset<Row> df2 = df.withColumn(" format: day",col(" format: day").cast("int"))
                .withColumnRenamed(" format: day","day")
                .withColumnRenamed("value location:91_78 Start : 20170801 ","value")
                .withColumn("month",expr("month*100+day"))
                .withColumnRenamed("month","date")
                .drop("day");

        return df2;
    }
    @Override
    public Dataset<Row> getLocationDataset(Dataset<Row> ds){
        List<LocationDto> list = locationDao.selectLocationList();
        for (LocationDto location : list){
            //System.out.println(location.getLocationLevel1());

            Dataset<Row> avg = ds.filter("location=='"+ location.getLocationLevel1()+"'").groupBy(" format: day", "month").agg(avg("value location:91_78 Start : 20170801 "),
                    min("value location:91_78 Start : 20170801 "), max("value location:91_78 Start : 20170801 "));
            avg.show();
            //Dataset<Row> min = ds.filter("location=='"+ location.getLocationLevel1()+"'").groupBy(" format: day").min("value location:91_78 Start : 20170801 ");
        }
        return null;
    }

}
