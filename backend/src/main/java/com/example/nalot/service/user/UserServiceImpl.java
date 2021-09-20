package com.example.nalot.service.user;

import com.example.nalot.dao.UserDao;
import com.example.nalot.model.clothes.ClothesDto;
import com.example.nalot.model.user.*;
import com.example.nalot.model.weather.WeatherDto;
import com.example.nalot.service.weather.WeatherService;
import com.example.nalot.service.clothes.ClothesService;
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
    public int deleteUserInfo(String userId) {
        this.deleteUserHistoryListByUserId(userId);
        return userDao.deleteUserInfo(userId);
    }

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
    public UserHistoryDto selectUserHistoryInfo(int userHistoryId, String userId) {
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
    public UserHistoryResponse getUserHistoryResponseByUserHistoryId(String userId, int userHistoryId) {
        UserHistoryDto userHistoryDto = selectUserHistoryInfo(userHistoryId,userId);

        WeatherDto weather = weatherService.selectWeatherInfo(userHistoryDto.getWeatherId());
        UserClothesResponse userClothes = getUserClothesResponseById(userHistoryDto.getUserClothesId());

        UserHistoryResponse response = new UserHistoryResponse();
        response.setId(userHistoryDto.getId());
        response.setUserId(userHistoryDto.getUserId());
        response.setWeather(weather);
        response.setUserClothes(userClothes);
        response.setHistoryDate(userHistoryDto.getHistoryDate());

        return response;
    }

    @Override
    public int insertUserHistoryInfo(UserHistoryRequest request) {
        SimpleDateFormat format = new SimpleDateFormat ( "yyyyMMddHHmmss");
        Date time = new Date();
        String date = format.format(time);
        String id = date + request.getUserId();

        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setId(id);
        weatherDto.setTemperature(request.getTemperature());
        weatherDto.setUserId(request.getUserId());
        weatherDto.setBaseDate(request.getBaseDate());
        weatherDto.setBaseTime(request.getBaseTime());
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
    public int deleteUserHistoryInfo(int userHistoryId, String userId) {
        UserHistoryDto userHistoryDto = this.selectUserHistoryInfo(userHistoryId,userId);
        int count = userDao.deleteUserHistoryInfo(userHistoryId,userId);
        weatherService.deleteWeatherInfo(userHistoryDto.getWeatherId());
        this.deleteUserClothesInfo(userHistoryDto.getUserClothesId());
        return count;
    }

    @Override
    public int deleteUserHistoryListByUserId(String userId) {
        int count = userDao.deleteUserHistoryListByUserId(userId);
        weatherService.deleteWeatherListByUserId(userId);
        this.deleteUserClothesListByUserId(userId);
        return count;
    }

}
