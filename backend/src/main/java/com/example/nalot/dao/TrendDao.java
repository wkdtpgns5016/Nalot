package com.example.nalot.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrendDao {
    private final SqlSession sqlSession;
    public TrendDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public double getAverage() {
        return sqlSession.selectOne("com.example.nalot.dao.TrendDao.getAverage");
    }

    public double getStdDev() {
        return sqlSession.selectOne("com.example.nalot.dao.TrendDao.getAverage");
    }

    public List<String> selectClothesList() {
        return sqlSession.selectList("com.example.nalot.dao.TrendDao.selectClothesList");
    }

}
