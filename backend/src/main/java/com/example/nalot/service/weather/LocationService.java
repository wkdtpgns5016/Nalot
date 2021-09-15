package com.example.nalot.service.weather;

import com.example.nalot.model.weather.LocationDto;

public interface LocationService {
    public LocationDto selectLocationByLevel1(String location);
}
