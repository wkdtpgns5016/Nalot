package com.example.nalot.service;

import com.example.nalot.NalotApplication;
import com.example.nalot.model.UserClothesDto;
import com.example.nalot.model.UserClothesResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NalotApplication.class)
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void getUserClothesResponseByIdTest() {
        //given
        int userClothesId = 1;

        //when
        UserClothesResponse response = userService.getUserClothesResponseById(userClothesId);

        //then
        assertThat(response.getId()).isOne();
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
        int userClothesId = 1;

        //when
        int result = userService.deleteUserClothesInfo(userClothesId);

        //then
        assertThat(result).isOne();
    }
}
