package com.example.nalot.dao;

import com.example.nalot.model.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public class UserDao {
    private final SqlSession sqlSession;
    public UserDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<UserDto> selectUserList() {
        return sqlSession.selectList("com.example.nalot.dao.UserDao.selectUserList");
    }

    public List<UserDto> selectUserInfo(String userId) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        return sqlSession.selectOne("com.example.nalot.dao.UserDao.selectUserList",userId);
    }
}
