package com.example.nalot.dao;

import com.example.nalot.model.data.TrendDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes =  TrendDao.class)
class TrendDaoTest {

    @Autowired
    TrendDao trendDao;

    @Test
    void getAverage() {
        //given

        //when
        double result = trendDao.getAverage();

        //then
        assertThat(result).isNotNull();

    }

    @Test
    void getStdDev() {
        //given

        //when
        double result = trendDao.getStdDev();

        //then
        assertThat(result).isNotNull();
    }

    @Test
    void selectClothesList() {
        //given

        //when
        List<String> result = trendDao.selectClothesList();

        //then
        assertThat(result).isNotNull();
    }

    @Test
    void selectTrendList() {
        //given

        //when
        List<TrendDto> result = trendDao.selectTrendList();

        //then
        assertThat(result).isNotNull();
    }
}