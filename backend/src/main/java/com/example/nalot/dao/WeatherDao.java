package com.example.nalot.dao;

import com.example.nalot.model.weather.WeatherDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import java.util.HashMap;

@Repository
public class WeatherDao {
    private final SqlSession sqlSession;
    public WeatherDao(SqlSession sqlSession) { this.sqlSession = sqlSession; }

    public WeatherDto selectWeatherInfo(String weatherId) {
        HashMap<String,Object> param = new HashMap<>();
        param.put("weatherId",weatherId);
        return sqlSession.selectOne("com.example.nalot.dao.WeatherDao.selectWeatherInfo", param);
    }

    public int insertWeatherInfo(WeatherDto weatherDto) {
        HashMap<String,Object> param = new HashMap<>();
        param.put("weatherDto",weatherDto);
        return sqlSession.insert("com.example.nalot.dao.WeatherDao.insertWeatherInfo", param);
    }

    public int deleteWeatherInfo(String weatherId) {
        HashMap<String,Object> param = new HashMap<>();
        param.put("weatherId",weatherId);
        return sqlSession.delete("com.example.nalot.dao.WeatherDao.deleteWeatherInfo", param);
    }

    public int deleteWeatherListByUserId(String userId) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        return sqlSession.delete("com.example.nalot.dao.WeatherDao.deleteWeatherListByUserId",param);
    }
}
