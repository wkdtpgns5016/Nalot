package com.example.nalot.service;

import com.example.nalot.dao.UserDao;
import com.example.nalot.model.ClothesDto;
import com.example.nalot.model.UserClothesDto;
import com.example.nalot.model.UserClothesResponse;
import com.example.nalot.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final ClothesService clothesService;
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, ClothesService clothesService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.clothesService = clothesService;
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
    public int insertUserInfo(UserDto user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        return userDao.insertUserInfo(user);
    }

    @Override
    public int updateUserInfo(String userId, UserDto user) { return userDao.updateUserInfo(userId, user); }

    @Override
    public int deleteUserInfo(String userId) { return userDao.deleteUserInfo(userId); }

    @Override
    public UserClothesDto selectUserClothesInfo(int userClothesId) {
        return userDao.selectUserClothesInfo(userClothesId);
    }

    @Override
    public UserClothesResponse getUserClothesResponseById(int userClothesId) {
        UserClothesDto userClothesDto = selectUserClothesInfo(userClothesId);

        ClothesDto clothesDto = clothesService.selectClothesInfo(userClothesDto.getClothesId());
        UserClothesResponse.Clothes clothes = new UserClothesResponse.Clothes();
        clothes.setClothesDto(clothesDto);

        UserClothesResponse response = new UserClothesResponse();
        response.setId(userClothesDto.getId());
        response.setClothes(clothes);
        response.setColor(userClothesDto.getColor());
        response.setColorMix(userClothesDto.getColorMix());

        return response;
    }

}
