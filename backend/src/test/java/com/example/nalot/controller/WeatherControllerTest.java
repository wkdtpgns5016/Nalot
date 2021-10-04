package com.example.nalot.controller;

import com.example.nalot.NalotApplication;
import com.example.nalot.model.authentication.JwtRequest;
import com.example.nalot.model.weather.WeatherApiRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NalotApplication.class)
@AutoConfigureMockMvc
class WeatherControllerTest {
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
    void getWeatherForecast() throws Exception {
        //given
        String userId = "test";
        String content = objectMapper.writeValueAsString(new JwtRequest("test", "1234"));
        ResultActions actions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        String token = objectMapper.readTree(actions.andReturn().getResponse().getContentAsString()).get("message").asText();

        WeatherApiRequest request = new WeatherApiRequest();
        request.setLocation("서울특별시");

        //when
        actions = mockMvc.perform(post("/weathers")
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getWeatherList() throws Exception {
        //given
        String userId = "test";
        String content = objectMapper.writeValueAsString(new JwtRequest("test", "1234"));
        ResultActions actions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        String token = objectMapper.readTree(actions.andReturn().getResponse().getContentAsString()).get("message").asText();

        //when
        actions = mockMvc.perform(get("/weathers/forecasts")
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getWeatherDto() throws Exception {
        //given
        String userId = "test";
        String content = objectMapper.writeValueAsString(new JwtRequest("test", "1234"));
        ResultActions actions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        String token = objectMapper.readTree(actions.andReturn().getResponse().getContentAsString()).get("message").asText();

        WeatherApiRequest request = new WeatherApiRequest();
        request.setLocation("서울특별시");

        //when
        actions = mockMvc.perform(post("/weathers/forecasts")
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());
    }
}