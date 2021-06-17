package com.example.nalot.service;

import com.example.nalot.model.UserClothesDto;
import com.example.nalot.model.UserClothesResponse;
import com.example.nalot.model.UserDto;

import java.util.List;

public interface UserService {
    public List<UserDto> selectUserList();
    public UserDto selectUserInfo(String userId);
    public int insertUserInfo(UserDto user);
    public int updateUserInfo(String userId, UserDto user);
    public int deleteUserInfo(String userId);
    public UserClothesDto selectUserClothesInfo(int userClothesId);
    public UserClothesResponse getUserClothesResponseById(int userClothesId);
    public int insertUserClothesInfo(UserClothesDto userClothes);
    public int deleteUserClothesInfo(int userClothesId);
}
