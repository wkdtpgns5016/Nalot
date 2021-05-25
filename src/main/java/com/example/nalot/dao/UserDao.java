package com.example.nalot.dao;

import com.example.nalot.model.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class UserDao {
    private final SqlSession sqlSession;
    public UserDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<UserDto> selectUserList() {
        return sqlSession.selectList("com.example.nalot.dao.UserDao.selectUserList");
    }

    public UserDto selectUserInfo(String userId) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        return sqlSession.selectOne("com.example.nalot.dao.UserDao.selectUserInfo",userId);
    }

    public int insertUserInfo(UserDto user) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("user", user);
        return sqlSession.insert("com.example.nalot.dao.UserDao.insertUserInfo",user);
    }

    public int updateUserInfo(UserDto user) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("user", user);
        return sqlSession.update("com.example.nalot.dao.UserDao.updateUserInfo",user);
    }

    public int deleteUserInfo(String userId) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        System.out.println(userId);
        return sqlSession.delete("com.example.nalot.dao.UserDao.deleteUserInfo",param);
    }
}
