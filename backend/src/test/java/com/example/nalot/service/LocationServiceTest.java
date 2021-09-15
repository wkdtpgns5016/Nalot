package com.example.nalot.service;

import com.example.nalot.NalotApplication;
import com.example.nalot.model.weather.LocationDto;
import com.example.nalot.service.weather.LocationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NalotApplication.class)
public class LocationServiceTest {
    @Autowired
    LocationService locationService;

    @Test
    public void selectLocationByLevel1(){
        //given
        String location = "서울특별시";

        //when
        LocationDto grid = locationService.selectLocationByLevel1(location);

        //then
        System.out.println(grid.getGridX());
        System.out.println(grid.getGridY());

        assertThat(grid).isNotNull();
    }
}
