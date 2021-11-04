package com.example.nalot.controller;


import com.example.nalot.config.JwtTokenUtil;
import com.example.nalot.model.authentication.JwtRequest;
import com.example.nalot.model.user.UserDto;
import com.example.nalot.model.user.UserHistoryRequest;
import com.example.nalot.service.authentication.UserDetailsServiceImpl;
import com.example.nalot.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.sql.Timestamp;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    void selectUserList() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(new JwtRequest("test", "1234"));
        ResultActions actions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        String token = objectMapper.readTree(actions.andReturn().getResponse().getContentAsString()).get("message").asText();

        //when
        actions = mockMvc.perform(get("/users")
        .header(HttpHeaders.AUTHORIZATION,"Bearer "+token));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void selectUserInfo() throws Exception {
        //given
        String userId = "test";
        String content = objectMapper.writeValueAsString(new JwtRequest("test", "1234"));
        ResultActions actions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        String token = objectMapper.readTree(actions.andReturn().getResponse().getContentAsString()).get("message").asText();

        //when
        actions = mockMvc.perform(get("/users/"+userId)
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void signUp() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(new JwtRequest("test", "1234"));
        ResultActions actions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        String token = objectMapper.readTree(actions.andReturn().getResponse().getContentAsString()).get("message").asText();

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
        actions = mockMvc.perform(post("/users")
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        //then
        actions.andExpect(status().is(201))
                .andDo(print());
    }

    @Test
    void modifyUserInfo() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(new JwtRequest("test", "1234"));
        ResultActions actions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        String token = objectMapper.readTree(actions.andReturn().getResponse().getContentAsString()).get("message").asText();

        UserDto user = new UserDto();
        user.setId("member");
        user.setPassword("1234");
        user.setName("변경");
        user.setAddressBasic("기본주소");
        user.setAddressDetail("상세주소");
        user.setAddressGroundNumber("도로명주소");
        user.setGender("M");
        user.setZoneCode("12345");
        user.setBirth(new Timestamp(System.currentTimeMillis()));

        //when
        actions = mockMvc.perform(put("/users/"+user.getId())
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        //then
        actions.andExpect(status().is(201))
                .andDo(print());
    }

    @Test
    void removeUser() throws Exception {
        //given
        String userId = "member";
        String content = objectMapper.writeValueAsString(new JwtRequest("test", "1234"));
        ResultActions actions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        String token = objectMapper.readTree(actions.andReturn().getResponse().getContentAsString()).get("message").asText();

        //when
        actions = mockMvc.perform(delete("/users/"+userId)
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token));

        //then
        actions.andExpect(status().is(201))
                .andDo(print());
    }

    @Test
    void logIn() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(new JwtRequest("test", "1234"));

        //when
        ResultActions actions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getUserHistoryResponseByUserId() throws Exception {
        //given
        String userId = "test";
        String content = objectMapper.writeValueAsString(new JwtRequest("test", "1234"));
        ResultActions actions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        String token = objectMapper.readTree(actions.andReturn().getResponse().getContentAsString()).get("message").asText();

        //when
        actions = mockMvc.perform(get("/users/histories/"+userId)
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getUserHistoryResponseByUserHistoryId() throws Exception {
        //given
        String userId = "test";
        int userHistoryId = 23;
        String content = objectMapper.writeValueAsString(new JwtRequest("test", "1234"));
        ResultActions actions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        String token = objectMapper.readTree(actions.andReturn().getResponse().getContentAsString()).get("message").asText();

        //when
        actions = mockMvc.perform(get("/users/histories/"+userId+"/"+userHistoryId)
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void createResult() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(new JwtRequest("test", "1234"));
        ResultActions actions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        String token = objectMapper.readTree(actions.andReturn().getResponse().getContentAsString()).get("message").asText();

        UserHistoryRequest request = new UserHistoryRequest();
        request.setUserId("test2");
        request.setTemperature(26);
        request.setClothesId(2);
        request.setColor("#1245e8");
        request.setColorMix("$57e426");
        request.setBaseDate("20211004");
        request.setBaseTime("2000");

        //when
        actions = mockMvc.perform(post("/users/histories")
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        //then
        actions.andExpect(status().is(201))
                .andDo(print());
    }

    @Test
    void removeUserHistory() throws Exception {
        //given
        String userId = "test2";
        int userHistoryId = 28;
        String content = objectMapper.writeValueAsString(new JwtRequest("test", "1234"));
        ResultActions actions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        String token = objectMapper.readTree(actions.andReturn().getResponse().getContentAsString()).get("message").asText();

        //when
        actions = mockMvc.perform(delete("/users/histories/"+userId+"/"+userHistoryId)
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token));

        //then
        actions.andExpect(status().is(201))
                .andDo(print());
    }

    @Test
    void removeUserHistoryByUserId() throws Exception {
        //given
        String userId = "test3";
        String content = objectMapper.writeValueAsString(new JwtRequest("test", "1234"));
        ResultActions actions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        String token = objectMapper.readTree(actions.andReturn().getResponse().getContentAsString()).get("message").asText();

        //when
        actions = mockMvc.perform(delete("/users/histories/"+userId)
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token));

        //then
        actions.andExpect(status().is(201))
                .andDo(print());
    }
}