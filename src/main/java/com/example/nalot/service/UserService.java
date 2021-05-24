package com.example.nalot.service;

import com.example.nalot.model.UserDto;

import java.util.List;

public interface UserService {
    public List<UserDto> selectUserList();
    public UserDto selectUserInfo(String userId);
    public int insertUserInfo(UserDto user);
    public int updateUserInfo(UserDto user);
}
