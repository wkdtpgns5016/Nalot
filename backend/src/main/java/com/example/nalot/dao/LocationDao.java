package com.example.nalot.dao;

import com.example.nalot.model.weather.LocationDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;

@Repository
public class LocationDao {
    private final SqlSession sqlSession;
    public LocationDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<LocationDto> selectLocationList() {
        return sqlSession.selectList("com.example.nalot.dao.LocationDao.selectLocationList");
    }

    public LocationDto selectLocationByLevel1(String location) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("location",location);
        return sqlSession.selectOne("com.example.nalot.dao.LocationDao.selectLocationByLevel1",param);
    }
}
