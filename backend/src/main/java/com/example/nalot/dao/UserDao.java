package com.example.nalot.dao;

import com.example.nalot.model.UserClothesDto;
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
        return sqlSession.selectOne("com.example.nalot.dao.UserDao.selectUserInfo",param);
    }

    public int insertUserInfo(UserDto user) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("user", user);
        return sqlSession.insert("com.example.nalot.dao.UserDao.insertUserInfo",param);
    }

    public int updateUserInfo(String userId, UserDto user) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId",userId);
        param.put("user", user);
        return sqlSession.update("com.example.nalot.dao.UserDao.updateUserInfo",param);
    }

    public int deleteUserInfo(String userId) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        return sqlSession.delete("com.example.nalot.dao.UserDao.deleteUserInfo",param);
    }

    public UserClothesDto selectUserClothesInfo(int userClothesId) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userClothesId", userClothesId);
        return sqlSession.selectOne("com.example.nalot.dao.UserDao.selectUserClothesInfo",param);
    }

    public int insertUserClothesInfo(UserClothesDto userClothes) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userClothes", userClothes);
        return sqlSession.insert("com.example.nalot.dao.UserDao.insertUserClothesInfo",param);
    }

    public int deleteUserClothesInfo(int userClothesId) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userClothesId", userClothesId);
        return sqlSession.delete("com.example.nalot.dao.UserDao.deleteUserClothesInfo",param);
    }
}
