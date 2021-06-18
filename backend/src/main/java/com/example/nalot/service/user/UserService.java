package com.example.nalot.service.user;

import com.example.nalot.model.user.*;

import java.util.List;

public interface UserService {
    public List<UserDto> selectUserList();
    public UserDto selectUserInfo(String userId);
    public int insertUserInfo(UserDto user);
    public int updateUserInfo(String userId, UserDto user);
    public int deleteUserInfo(String userId);
    public UserClothesDto selectUserClothesInfo(String userClothesId);
    public UserClothesResponse getUserClothesResponseById(String userClothesId);
    public int insertUserClothesInfo(UserClothesDto userClothes);
    public int deleteUserClothesInfo(String userClothesId);
    public int deleteUserClothesListByUserId(String userId);
    public List<UserHistoryDto> selectUserHistoryListByUserId(String userId);
    public UserHistoryDto selectUserHistoryInfo(String userHistoryId, String userId);
    public List<UserHistoryResponse> getUserHistoryResponseByUserId(String userId);
    public int insertUserHistoryInfo(UserHistoryRequest request);
    public int deleteUserHistoryInfo(String userHistoryId, String userId);
    public int deleteUserHistoryListByUserId(String userId);
}
