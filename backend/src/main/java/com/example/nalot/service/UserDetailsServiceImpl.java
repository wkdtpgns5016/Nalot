package com.example.nalot.service;

import com.example.nalot.dao.UserDao;
import com.example.nalot.model.CustomUserDetails;
import com.example.nalot.model.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDao userDao;
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userDao.selectUserInfo(username);
        if(user != null) {
            CustomUserDetails userDetails = new CustomUserDetails();
            userDetails.setUserDto(user);
            return userDetails;
        }
        else{
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}