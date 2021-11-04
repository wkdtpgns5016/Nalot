package com.example.nalot.service.user;

import com.example.nalot.dao.UserDao;
import com.example.nalot.model.clothes.ClothesDto;
import com.example.nalot.model.user.*;
import com.example.nalot.model.weather.WeatherDto;
import com.example.nalot.service.clothes.ClothesServiceImpl;
import com.example.nalot.service.weather.WeatherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @Mock
    UserDao userDao;

    @Mock
    ClothesServiceImpl clothesService;

    @Mock
    WeatherServiceImpl weatherService;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void selectUserList() {
        //given
        UserDto user1 = new UserDto();
        user1.setId("test1");

        UserDto user2 = new UserDto();
        user2.setId("test2");

        List<UserDto> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        given(userDao.selectUserList()).willReturn(users);

        //when
        List<UserDto> result = userService.selectUserList();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void selectUserInfo() {
        //given
        String userId = "test1";
        UserDto user1 = new UserDto();
        user1.setId(userId);

        given(userDao.selectUserInfo(userId)).willReturn(user1);

        //when
        UserDto result = userService.selectUserInfo(userId);

        //then
        assertThat(result.getId()).isEqualTo(userId);
    }

    @Test
    void insertUserInfo() {
        //given
        UserDto user1 = new UserDto();
        user1.setId("test3");
        user1.setPassword("1234");
        user1.setName("홍길동");
        user1.setZoneCode("12345");
        user1.setAddressDetail("AddressDetail");
        user1.setAddressGroundNumber("AddressGroundNumber");
        user1.setAddressBasic("AddressBasic");
        user1.setBirth(new Timestamp(System.currentTimeMillis()));
        user1.setGender("M");

        given(passwordEncoder.encode(user1.getPassword())).willReturn("IncodeTestPassword");
        given(userDao.insertUserInfo(user1)).willReturn(1);

        //when
        int result = userService.insertUserInfo(user1);

        //then
        assertThat(result).isOne();
    }

    @Test
    void updateUserInfo() {
        //given
        String userId = "test3";
        UserDto user1 = new UserDto();
        user1.setId(userId);
        user1.setPassword("1234");
        user1.setName("김철수");
        user1.setZoneCode("12345");
        user1.setAddressDetail("AddressDetail");
        user1.setAddressGroundNumber("AddressGroundNumber");
        user1.setAddressBasic("AddressBasic");
        user1.setBirth(new Timestamp(System.currentTimeMillis()));
        user1.setGender("M");

        given(passwordEncoder.encode(user1.getPassword())).willReturn("IncodeTestPassword");
        given(userDao.updateUserInfo(userId, user1)).willReturn(1);

        //when
        int result = userService.updateUserInfo(userId, user1);

        //then
        assertThat(result).isOne();
    }

    @Test
    void deleteUserInfo() {
        //given
        String userId = "test3";

        given(userDao.deleteUserInfo(userId)).willReturn(1);

        //when
        int result = userService.deleteUserInfo(userId);

        //then
        assertThat(result).isOne();
    }

    @Test
    void selectUserClothesInfo() {
        //given
        String id = "test";

        UserClothesDto userClothes = new UserClothesDto();
        userClothes.setId(id);

        given(userDao.selectUserClothesInfo(id)).willReturn(userClothes);

        //when
        UserClothesDto result = userService.selectUserClothesInfo(id);

        //then
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void getUserClothesResponseById() {
        //given
        String id = "test";
        String clothes = "티셔츠";
        String color = "#f01592";
        int clothesId = 0;

        UserClothesDto userClothes = new UserClothesDto();
        userClothes.setId(id);
        userClothes.setClothesId(clothesId);
        userClothes.setColor(color);
        userClothes.setColorMix(color);

        ClothesDto clothesDto = new ClothesDto();
        clothesDto.setId(clothesId);
        clothesDto.setName(clothes);

        given(userDao.selectUserClothesInfo(id)).willReturn(userClothes);
        given(clothesService.selectClothesInfo(clothesId)).willReturn(clothesDto);

        //when
        UserClothesResponse result = userService.getUserClothesResponseById(id);

        //then
        assertThat(result.getColor()).isEqualTo(color);
    }

    @Test
    void insertUserClothesInfo() {
        //given
        String id = "test";

        UserClothesDto userClothes = new UserClothesDto();
        userClothes.setId(id);

        given(userDao.insertUserClothesInfo(userClothes)).willReturn(1);

        //when
        int result = userService.insertUserClothesInfo(userClothes);

        //then
        assertThat(result).isOne();
    }

    @Test
    void deleteUserClothesInfo() {
        //given
        String id = "test";

        given(userDao.deleteUserClothesInfo(id)).willReturn(1);

        //when
        int result = userService.deleteUserClothesInfo(id);

        //then
        assertThat(result).isOne();
    }

    @Test
    void deleteUserClothesListByUserId() {
        //given
        String userId = "test";

        given(userDao.deleteUserClothesListByUserId(userId)).willReturn(1);

        //when
        int result = userService.deleteUserClothesListByUserId(userId);

        //then
        assertThat(result).isOne();
    }

    @Test
    void selectUserHistoryListByUserId() {
        //given
        String userId = "test";
        UserHistoryDto userHistory1 = new UserHistoryDto();
        UserHistoryDto userHistory2 = new UserHistoryDto();

        List<UserHistoryDto> userHistoryList = new ArrayList<>();
        userHistoryList.add(userHistory1);
        userHistoryList.add(userHistory2);

        given(userDao.selectUserHistoryListByUserId(userId)).willReturn(userHistoryList);

        //when
        List<UserHistoryDto> result = userService.selectUserHistoryListByUserId(userId);

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void selectUserHistoryInfo() {
        //given
        int userHistoryId = 0;
        String userId = "test";

        UserHistoryDto userHistory1 = new UserHistoryDto();
        userHistory1.setId(userHistoryId);
        userHistory1.setUserId(userId);

        given(userDao.selectUserHistoryInfo(userHistoryId, userId)).willReturn(userHistory1);

        //when
        UserHistoryDto result = userService.selectUserHistoryInfo(userHistoryId, userId);

        //then
        assertThat(result.getUserId()).isEqualTo(userId);
    }

    @Test
    void getUserHistoryResponseByUserId() {
        //given
        List<UserHistoryDto> list = new ArrayList<>();

        UserHistoryDto userHistoryDto = new UserHistoryDto();
        userHistoryDto.setUserId("userId");
        userHistoryDto.setId(0);
        userHistoryDto.setUserClothesId("test");
        userHistoryDto.setWeatherId("test");

        list.add(userHistoryDto);

        given(userService.selectUserHistoryListByUserId("userId")).willReturn(list);

        WeatherDto weather = new WeatherDto();
        weather.setUserId("userId");
        weather.setId("test");
        weather.setBaseDate("20211004");
        weather.setBaseTime("2000");
        weather.setTemperature(19);

        given(weatherService.selectWeatherInfo(userHistoryDto.getWeatherId())).willReturn(weather);

        ClothesDto clothesDto = new ClothesDto();
        clothesDto.setName("티셔츠");
        clothesDto.setId(0);
        clothesDto.setCategory("1");

        UserClothesDto userClothesDto = new UserClothesDto();
        userClothesDto.setUserId("userId");
        userClothesDto.setId("test");
        userClothesDto.setClothesId(0);

        UserClothesResponse userClothes = new UserClothesResponse();
        userClothes.setId("test");
        userClothes.setClothes(clothesDto);

        given(userService.selectUserClothesInfo("test")).willReturn(userClothesDto);
        given(clothesService.selectClothesInfo(0)).willReturn(clothesDto);

        //when
        List<UserHistoryResponse> result = userService.getUserHistoryResponseByUserId("userId");

        //then
        assertThat(result.size()).isOne();

    }

    @Test
    void getUserHistoryResponseByUserHistoryId() {
        //given
        UserHistoryDto userHistoryDto = new UserHistoryDto();
        userHistoryDto.setUserId("userId");
        userHistoryDto.setId(0);
        userHistoryDto.setUserClothesId("test");
        userHistoryDto.setWeatherId("test");

        given(userService.selectUserHistoryInfo(0,"userId")).willReturn(userHistoryDto);

        WeatherDto weather = new WeatherDto();
        weather.setUserId("userId");
        weather.setId("test");
        weather.setBaseDate("20211004");
        weather.setBaseTime("2000");
        weather.setTemperature(19);

        given(weatherService.selectWeatherInfo(userHistoryDto.getWeatherId())).willReturn(weather);

        ClothesDto clothesDto = new ClothesDto();
        clothesDto.setName("티셔츠");
        clothesDto.setId(0);
        clothesDto.setCategory("1");

        UserClothesDto userClothesDto = new UserClothesDto();
        userClothesDto.setUserId("userId");
        userClothesDto.setId("test");
        userClothesDto.setClothesId(0);

        UserClothesResponse userClothes = new UserClothesResponse();
        userClothes.setId("test");
        userClothes.setClothes(clothesDto);

        given(userService.selectUserClothesInfo("test")).willReturn(userClothesDto);
        given(clothesService.selectClothesInfo(0)).willReturn(clothesDto);

        //when
        UserHistoryResponse result = userService.getUserHistoryResponseByUserHistoryId("userId",0);

        //then
        assertThat(result.getUserId()).isEqualTo("userId");
    }

    @Test
    void insertUserHistoryInfo() {
        //given
        String id = "test";
        UserHistoryRequest request = new UserHistoryRequest();
        request.setUserId("test");
        request.setColor("#ffffff");
        request.setColorMix("#ffffff");
        request.setBaseDate("20201004");
        request.setBaseTime("2000");
        request.setTemperature(17);
        request.setClothesId(0);

        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setId(id);
        weatherDto.setTemperature(request.getTemperature());
        weatherDto.setUserId(request.getUserId());
        weatherDto.setBaseDate(request.getBaseDate());
        weatherDto.setBaseTime(request.getBaseTime());

        UserClothesDto userClothesDto = new UserClothesDto();
        userClothesDto.setId(id);
        userClothesDto.setClothesId(request.getClothesId());
        userClothesDto.setColor(request.getColor());
        userClothesDto.setColorMix(request.getColorMix());
        userClothesDto.setUserId(request.getUserId());

        UserHistoryDto userHistory = new UserHistoryDto();
        userHistory.setUserId(request.getUserId());
        userHistory.setWeatherId(id);
        userHistory.setUserClothesId(id);

        given(weatherService.insertWeatherInfo(weatherDto)).willReturn(1);
        given(userService.insertUserClothesInfo(userClothesDto)).willReturn(1);
        given(userDao.insertUserHistoryInfo(userHistory)).willReturn(2);

        //when
        int result = userService.insertUserHistoryInfo(request);

        //then
        System.out.println(result);
        assertThat(result).isZero();
    }

    @Test
    void deleteUserHistoryInfo() {
        //given
        int userHistoryId = 0;
        String userId = "test";
        String userClothesId = "userClothesId";
        String weatherId = "weatherId";

        UserHistoryDto userHistoryDto = new UserHistoryDto();
        userHistoryDto.setUserClothesId(userClothesId);
        userHistoryDto.setWeatherId(weatherId);

        given(userDao.selectUserHistoryInfo(userHistoryId,userId)).willReturn(userHistoryDto);
        given(userDao.deleteUserHistoryInfo(userHistoryId,userId)).willReturn(1);
        given(weatherService.deleteWeatherInfo(userHistoryDto.getWeatherId())).willReturn(1);
        given(userService.deleteUserClothesInfo(userHistoryDto.getUserClothesId())).willReturn(1);

        //when
        int result = userService.deleteUserHistoryInfo(userHistoryId, userId);

        //then
        assertThat(result).isOne();
    }

    @Test
    void deleteUserHistoryListByUserId() {
        //given
        String userId = "test";

        given(userDao.deleteUserHistoryListByUserId(userId)).willReturn(1);
        given(weatherService.deleteWeatherListByUserId(userId)).willReturn(1);
        given(userService.deleteUserClothesListByUserId(userId)).willReturn(1);

        //when
        int result = userService.deleteUserHistoryListByUserId(userId);

        //then
        assertThat(result).isOne();
    }
}