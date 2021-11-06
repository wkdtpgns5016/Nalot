package com.example.nalot.controller;

import com.example.nalot.model.authentication.JwtRequest;
import com.example.nalot.model.data.TrendDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sparkproject.jetty.http.HttpHeader;
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
@SpringBootTest
@AutoConfigureMockMvc
class DataControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    void getWeatherDataset() throws Exception{
        //given
        String content = objectMapper.writeValueAsString(new JwtRequest("4", "1"));
        ResultActions actions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        String token = objectMapper.readTree(actions.andReturn().getResponse().getContentAsString()).get("message").asText();

        //when
        actions = mockMvc.perform(get("/data/data")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " +token));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void refineWeatherDataSet() throws Exception{
        //given
        String content = objectMapper.writeValueAsString(new JwtRequest("4", "1"));
        ResultActions actions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        String token = objectMapper.readTree(actions.andReturn().getResponse().getContentAsString()).get("message").asText();

        //when
        actions = mockMvc.perform(get("/data/refine")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " +token));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void joinDataSet() throws Exception{
        //given
        String content = objectMapper.writeValueAsString(new JwtRequest("4", "1"));
        ResultActions actions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        String token = objectMapper.readTree(actions.andReturn().getResponse().getContentAsString()).get("message").asText();

        //when
        actions = mockMvc.perform(get("/data/join")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " +token));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void refineTrainData() throws Exception{
        //given
        String content = objectMapper.writeValueAsString(new JwtRequest("4", "1"));
        ResultActions actions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        String token = objectMapper.readTree(actions.andReturn().getResponse().getContentAsString()).get("message").asText();

        //when
        actions = mockMvc.perform(get("/data/train")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " +token));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());
    }
}