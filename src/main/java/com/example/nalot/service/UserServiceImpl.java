package com.example.nalot.service;

import com.example.nalot.dao.UserDao;
import com.example.nalot.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<UserDto> selectUserList() {
        return userDao.selectUserList();
    }

    @Override
    public UserDto selectUserInfo(String userId) {
        return userDao.selectUserInfo(userId);
    }

    @Override
    public int insertUserInfo(UserDto user) { return userDao.insertUserInfo(user); }

    @Override
    public int updateUserInfo(String userId, UserDto user) { return userDao.updateUserInfo(userId, user); }

    @Override
    public int deleteUserInfo(String userId) { return userDao.deleteUserInfo(userId); }
}
