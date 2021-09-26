package com.example.nalot.service.weather;

import com.example.nalot.dao.LocationDao;
import com.example.nalot.model.weather.LocationDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationDao locationDao;

    public LocationServiceImpl(LocationDao locationDao) { this.locationDao = locationDao; }

    @Override
    public List<LocationDto> selectLocationList() { return locationDao.selectLocationList(); }

    @Override
    public LocationDto selectLocationByLevel1(String location) {
        return locationDao.selectLocationByLevel1(location);
    }
}
