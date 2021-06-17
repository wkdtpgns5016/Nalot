package com.example.nalot.service;

import com.example.nalot.dao.UserDao;
import com.example.nalot.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public UserClothesDto selectUserClothesInfo(int userClothesId) {
        return userDao.selectUserClothesInfo(userClothesId);
    }

    @Override
    public UserClothesResponse getUserClothesResponseById(int userClothesId) {
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
    public int deleteUserClothesInfo(int userClothesId) {
        return userDao.deleteUserClothesInfo(userClothesId);
    }

    @Override
    public List<UserHistoryDto> selectUserHistoryListByUserId(String userId) {
        return userDao.selectUserHistoryListByUserId(userId);
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

}
