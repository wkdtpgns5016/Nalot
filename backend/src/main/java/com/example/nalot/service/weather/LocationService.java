package com.example.nalot.service.weather;

import com.example.nalot.model.weather.LocationDto;

import java.util.List;

public interface LocationService {
    public List<LocationDto> selectLocationList();
    public LocationDto selectLocationByLevel1(String location);
}
