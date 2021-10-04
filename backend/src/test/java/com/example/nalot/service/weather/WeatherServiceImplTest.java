package com.example.nalot.service.weather;

import com.example.nalot.dao.WeatherDao;
import com.example.nalot.model.weather.WeatherApiResponse;
import com.example.nalot.model.weather.WeatherDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class WeatherServiceImplTest {

    @InjectMocks
    WeatherServiceImpl weatherService;

    @Mock
    WeatherDao weatherDao;

    @Test
    void setDateNow() {
        //given

        //when
        ArrayList<String> dateNow = weatherService.setDateNow();

        //then
        assertThat(dateNow.size()).isEqualTo(2);
    }

    @Test
    void getWeatherForecast() {
        //given
        String date = "20211004";
        String time = "2000";
        String nx = "60";
        String ny = "127";

        //when
        WeatherApiResponse.Items result = weatherService.getWeatherForecast(date, time, nx, ny);

        //then
        assertThat(result).isNotNull();
    }

    @Test
    void setWeatherDto() {
        /**
         *
         * 외부 문서 및 API 이용 메소드는 단위 테스트가 아닌 별도 통합 테스트를 진행
         *
         * */
    }

    @Test
    void selectWeatherInfo() {
        //given
        String id = "test";
        String userId = "userId";
        String date = "20211004";
        String time = "2000";
        float temp = 18;

        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setId(id);
        weatherDto.setUserId(userId);
        weatherDto.setBaseDate(date);
        weatherDto.setBaseTime(time);
        weatherDto.setTemperature(temp);

        given(weatherDao.selectWeatherInfo(id)).willReturn(weatherDto);

        //when
        WeatherDto result = weatherService.selectWeatherInfo(id);

        //then
        assertThat(result.getUserId()).isEqualTo(userId);
    }

    @Test
    void insertWeatherInfo() {
        //given
        String id = "test";
        String userId = "userId";
        String date = "20211004";
        String time = "2000";
        float temp = 18;

        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setId(id);
        weatherDto.setUserId(userId);
        weatherDto.setBaseDate(date);
        weatherDto.setBaseTime(time);
        weatherDto.setTemperature(temp);

        given(weatherDao.insertWeatherInfo(weatherDto)).willReturn(1);

        //when
        int result = weatherService.insertWeatherInfo(weatherDto);

        //then
        assertThat(result).isOne();
    }

    @Test
    void deleteWeatherInfo() {
        //given
        String id = "test";

        given(weatherDao.deleteWeatherInfo(id)).willReturn(1);

        //when
        int result = weatherService.deleteWeatherInfo(id);

        //then
        assertThat(result).isOne();
    }

    @Test
    void deleteWeatherListByUserId() {
        //given
        String userId = "userId";
        given(weatherDao.deleteWeatherListByUserId(userId)).willReturn(1);

        //when
        int result = weatherService.deleteWeatherListByUserId(userId);

        //then
        assertThat(result).isOne();
    }
}