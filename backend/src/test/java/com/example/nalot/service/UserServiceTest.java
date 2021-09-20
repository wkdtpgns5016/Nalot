package com.example.nalot.service;

import com.example.nalot.NalotApplication;
import com.example.nalot.model.user.UserClothesDto;
import com.example.nalot.model.user.UserClothesResponse;
import com.example.nalot.model.user.UserHistoryDto;
import com.example.nalot.model.user.UserHistoryResponse;
import com.example.nalot.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NalotApplication.class)
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void getUserClothesResponseByIdTest() {
        //given
        String userClothesId = "1";

        //when
        UserClothesResponse response = userService.getUserClothesResponseById(userClothesId);

        //then
        assertThat(response.getId()).isEqualTo("1");
    }

    @Test
    public void insertUserClothesInfo() {
        //given
        UserClothesDto userClothes = new UserClothesDto();
        userClothes.setClothesId(1);
        userClothes.setColor("#FFFFFF");
        userClothes.setColorMix("#FFFFFF");

        //when
        int result = userService.insertUserClothesInfo(userClothes);

        //then
        assertThat(result).isOne();
    }

    @Test
    public void deleteUserClothesInfo() {
        //given
        String userClothesId = "1";

        //when
        int result = userService.deleteUserClothesInfo(userClothesId);

        //then
        assertThat(result).isEqualTo("1");
    }

    @Test
    public void getUserHistoryResponseByUserIdTest() {
        //given
        String userId = "test";

        //when
        List<UserHistoryResponse> list = userService.getUserHistoryResponseByUserId(userId);

        //then
        assertThat(list).isNotNull();
    }

    @Test
    public void selectUserHistoryInfoTest() {
        //given
        String userId = "test";
        int userHistoryId = 1;

        //when
        UserHistoryDto userHistoryDto = userService.selectUserHistoryInfo(userHistoryId,userId);

        //then
        assertThat(userHistoryDto).isNotNull();

    }
}
