package com.example.nalot.dao;

import com.example.nalot.model.user.UserClothesDto;
import com.example.nalot.model.user.UserDto;
import com.example.nalot.model.user.UserHistoryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(classes = UserDao.class)
class UserDaoTest {

    @Autowired
    UserDao userDao;

    @Test
    void selectUserList() {
        //given

        //when
        List<UserDto> result = userDao.selectUserList();

        //then
        assertThat(result).isNotNull();
    }

    @Test
    void selectUserInfo() {
        //given
        String userId = "test";

        //when
        UserDto result = userDao.selectUserInfo(userId);

        //then
        assertThat(result.getId()).isEqualTo(userId);
    }

    @Test
    void insertUserInfo() {
        //given
        UserDto user = new UserDto();
        user.setId("member");
        user.setPassword("1234");
        user.setName("테스트");
        user.setAddressBasic("기본주소");
        user.setAddressDetail("상세주소");
        user.setAddressGroundNumber("도로명주소");
        user.setGender("M");
        user.setZoneCode("12345");
        user.setBirth(new Timestamp(System.currentTimeMillis()));

        //when
        int result = userDao.insertUserInfo(user);

        //then
        assertThat(result).isOne();
    }

    @Test
    void updateUserInfo() {
        //given
        UserDto user = new UserDto();
        user.setId("test");
        user.setPassword("1234");
        user.setName("변경");
        user.setAddressBasic("기본주소");
        user.setAddressDetail("상세주소");
        user.setAddressGroundNumber("도로명주소");
        user.setGender("M");
        user.setZoneCode("12345");
        user.setBirth(new Timestamp(System.currentTimeMillis()));

        //when
        int result = userDao.updateUserInfo(user.getId(),user);

        //then
        assertThat(result).isOne();
    }

    @Test
    void deleteUserInfo() {
        //given
        String userId = "test";

        //when
        int result = userDao.deleteUserInfo(userId);

        //then
        assertThat(result).isOne();
    }

    @Test
    void selectUserClothesInfo() {
        //given
        String userClothesId = "20210617152956test";

        //when
        UserClothesDto result = userDao.selectUserClothesInfo(userClothesId);

        //then
        assertThat(result.getId()).isEqualTo(userClothesId);
    }

    @Test
    void insertUserClothesInfo() {
        //given
        UserClothesDto userClothesDto = new UserClothesDto();
        userClothesDto.setId("testId");
        userClothesDto.setUserId("test");
        userClothesDto.setColor("#f12482");
        userClothesDto.setColorMix("#e724a4");
        userClothesDto.setClothesId(2);

        //when
        int result = userDao.insertUserClothesInfo(userClothesDto);

        //then
        assertThat(result).isOne();
    }

    @Test
    void deleteUserClothesInfo() {
        //given
        String userClothesId = "20210617152956test";

        //when
        int result = userDao.deleteUserClothesInfo(userClothesId);

        //then
        assertThat(result).isOne();
    }

    @Test
    void deleteUserClothesListByUserId() {
        //given
        String userId = "test";

        //when
        int result = userDao.deleteUserClothesListByUserId(userId);

        //then
        assertThat(result).isZero();
    }

    @Test
    void selectUserHistoryListByUserId() {
        //given
        String userId = "test";

        //when
        List<UserHistoryDto> result = userDao.selectUserHistoryListByUserId(userId);

        //then
        assertThat(result.size()).isOne();
    }

    @Test
    void selectUserHistoryInfo() {
        //given
        int userHistoryId = 11;
        String userId = "test";

        //when
        UserHistoryDto result = userDao.selectUserHistoryInfo(userHistoryId, userId);

        //then
        assertThat(result.getId()).isEqualTo(userHistoryId);
    }

    @Test
    void insertUserHistoryInfo() {
        //given
        UserHistoryDto userHistoryDto = new UserHistoryDto();
        userHistoryDto.setUserId("test");
        userHistoryDto.setWeatherId("20210617153639test");
        userHistoryDto.setUserClothesId("20210617153639test");
        userHistoryDto.setHistoryDate(new Timestamp(System.currentTimeMillis()));

        //when
        int result = userDao.insertUserHistoryInfo(userHistoryDto);

        //then
        assertThat(result).isOne();
    }

    @Test
    void deleteUserHistoryInfo() {
        //given
        int userHistoryId = 11;
        String userId = "test";

        //when
        int result = userDao.deleteUserHistoryInfo(userHistoryId, userId);

        //then
        assertThat(result).isOne();
    }

    @Test
    void deleteUserHistoryListByUserId() {
        //given
        String userId = "test";

        //when
        int result = userDao.deleteUserHistoryListByUserId(userId);

        //then
        assertThat(result).isOne();
    }
}