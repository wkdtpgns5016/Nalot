package com.example.nalot.service;

import com.example.nalot.dao.ClothesDao;
import com.example.nalot.dao.UserDao;
import com.example.nalot.dao.WeatherDao;
import com.example.nalot.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final ClothesService clothesService;
    private final WeatherService weatherService;
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, ClothesService clothesService, WeatherService weatherService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.clothesService = clothesService;
        this.weatherService = weatherService;
    }

    @Override
    public List<UserDto> selectUserList() {
        return userDao.selectUserList();
    }

    @Override
    public UserDto selectUserInfo(String userId) {
        return userDao.selectUserInfo(userId);
    }

    @Override
    public int insertUserInfo(UserDto user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        return userDao.insertUserInfo(user);
    }

    @Override
    public int updateUserInfo(String userId, UserDto user) { return userDao.updateUserInfo(userId, user); }

    @Override
    public int deleteUserInfo(String userId) { return userDao.deleteUserInfo(userId); }

    @Override
    public UserClothesDto selectUserClothesInfo(String userClothesId) {
        return userDao.selectUserClothesInfo(userClothesId);
    }

    @Override
    public UserClothesResponse getUserClothesResponseById(String userClothesId) {
        UserClothesDto userClothesDto = selectUserClothesInfo(userClothesId);

        ClothesDto clothes = clothesService.selectClothesInfo(userClothesDto.getClothesId());

        UserClothesResponse response = new UserClothesResponse();
        response.setId(userClothesDto.getId());
        response.setClothes(clothes);
        response.setColor(userClothesDto.getColor());
        response.setColorMix(userClothesDto.getColorMix());

        return response;
    }

    @Override
    public int insertUserClothesInfo(UserClothesDto userClothes) {
        return userDao.insertUserClothesInfo(userClothes);
    }

    @Override
    public int deleteUserClothesInfo(String userClothesId) {
        return userDao.deleteUserClothesInfo(userClothesId);
    }

    @Override
    public int deleteUserClothesListByUserId(String userId) {
        return userDao.deleteUserClothesListByUserId(userId);
    }

    @Override
    public List<UserHistoryDto> selectUserHistoryListByUserId(String userId) {
        return userDao.selectUserHistoryListByUserId(userId);
    }

    @Override
    public UserHistoryDto selectUserHistoryInfo(String userHistoryId, String userId) {
        return userDao.selectUserHistoryInfo(userHistoryId,userId);
    }

    @Override
    public List<UserHistoryResponse> getUserHistoryResponseByUserId(String userId) {
        List<UserHistoryDto> list = selectUserHistoryListByUserId(userId);
        List<UserHistoryResponse> responseList = new ArrayList<>();

        for(UserHistoryDto userHistoryDto : list){
            WeatherDto weather = weatherService.selectWeatherInfo(userHistoryDto.getWeatherId());
            UserClothesResponse userClothes = getUserClothesResponseById(userHistoryDto.getUserClothesId());

            UserHistoryResponse response = new UserHistoryResponse();
            response.setId(userHistoryDto.getId());
            response.setUserId(userHistoryDto.getUserId());
            response.setWeather(weather);
            response.setUserClothes(userClothes);
            response.setHistoryDate(userHistoryDto.getHistoryDate());

            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public int insertUserHistoryInfo(UserHistoryRequest request) {
        SimpleDateFormat format = new SimpleDateFormat ( "yyyyMMddHHmmss");
        Date time = new Date();
        String date = format.format(time);
        String id = date + request.getUserId();

        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setId(id);
        weatherDto.setTemperatureMin(request.getTemperatureMin());
        weatherDto.setTemperatureMax(request.getTemperatureMax());
        weatherDto.setTemperatureCurrent(request.getTemperatureCurrent());
        weatherDto.setUserId(request.getUserId());
        weatherService.insertWeatherInfo(weatherDto);

        UserClothesDto userClothesDto = new UserClothesDto();
        userClothesDto.setId(id);
        userClothesDto.setClothesId(request.getClothesId());
        userClothesDto.setColor(request.getColor());
        userClothesDto.setColorMix(request.getColorMix());
        userClothesDto.setUserId(request.getUserId());
        this.insertUserClothesInfo(userClothesDto);

        UserHistoryDto userHistory = new UserHistoryDto();
        userHistory.setUserId(request.getUserId());
        userHistory.setWeatherId(id);
        userHistory.setUserClothesId(id);

        return userDao.insertUserHistoryInfo(userHistory);
    }

    @Override
    public int deleteUserHistoryInfo(String userHistoryId, String userId) {
        UserHistoryDto userHistoryDto = this.selectUserHistoryInfo(userHistoryId,userId);

        weatherService.deleteWeatherInfo(userHistoryDto.getWeatherId());
        this.deleteUserClothesInfo(userHistoryDto.getUserClothesId());
        return userDao.deleteUserHistoryInfo(userHistoryId,userId);
    }

    @Override
    public int deleteUserHistoryListByUserId(String userId) {
        weatherService.deleteWeatherListByUserId(userId);
        this.deleteUserClothesListByUserId(userId);
        return userDao.deleteUserHistoryListByUserId(userId);
    }

}
