package com.example.nalot.service.weather;

import com.example.nalot.dao.LocationDao;
import com.example.nalot.model.weather.LocationDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class LocationServiceImplTest {
    @InjectMocks
    LocationServiceImpl locationService;

    @Mock
    LocationDao locationDao;

    @Test
    void selectLocationList() {
        //given
        LocationDto location = new LocationDto();
        location.setLocationLevel1("서울특별시");
        location.setGridX("60");
        location.setGridY("127");

        List<LocationDto> list = new ArrayList<>();
        list.add(location);

        given(locationDao.selectLocationList()).willReturn(list);

        //when
        List<LocationDto> result = locationService.selectLocationList();

        //then
        assertThat(result.get(0).getLocationLevel1()).isEqualTo("서울특별시");
    }

    @Test
    void selectLocationByLevel1() {
        //given
        LocationDto location = new LocationDto();
        location.setLocationLevel1("서울특별시");
        location.setGridX("60");
        location.setGridY("127");

        given(locationDao.selectLocationByLevel1(location.getLocationLevel1())).willReturn(location);

        //when
        LocationDto result = locationService.selectLocationByLevel1(location.getLocationLevel1());

        //then
        assertThat(result.getLocationLevel1()).isEqualTo("서울특별시");
    }
}