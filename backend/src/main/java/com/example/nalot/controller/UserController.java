package com.example.nalot.controller;

import com.example.nalot.config.JwtTokenUtil;
import com.example.nalot.model.*;
import com.example.nalot.service.UserDetailsServiceImpl;
import com.example.nalot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserDetailsServiceImpl userDetailsService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }


    @GetMapping("")
    public List<UserDto> selectUserList(){ return userService.selectUserList(); }

    @GetMapping("{userId}")
    public UserDto selectUserInfo(@PathVariable String userId){
        return userService.selectUserInfo(userId);
    }

    @PostMapping("")
    public ResponseEntity<ResponseMessage> signUp(@RequestBody UserDto user) {
        try{
            int code = userService.insertUserInfo(user);
            ResponseMessage message = new ResponseMessage("Created", "회원가입이 완료 되었습니다.", "", "");
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.CREATED);

        }catch (Exception exception){
            ResponseMessage message = new ResponseMessage("Bad Request", "", "-1", "에러발생, 다시 시도해 주십시오.");
            exception.printStackTrace();
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.BAD_REQUEST);

        }
    }

    @PutMapping("{userId}")
    public ResponseEntity<ResponseMessage> modifyUserInfo(@PathVariable String userId, @RequestBody UserDto user) {
        try{
            int code = userService.updateUserInfo(userId, user);
            ResponseMessage message = new ResponseMessage("Created", "회원정보 수정이 완료되었습니다.", "", "");
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.CREATED);

        }catch (Exception exception){
            ResponseMessage message = new ResponseMessage("Bad Request", "", "-1", "에러발생, 다시 시도해 주십시오.");
            exception.printStackTrace();
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<ResponseMessage> deleteUserInfo(@PathVariable String userId) {
        try{
            int code = userService.deleteUserInfo(userId);
            ResponseMessage message = new ResponseMessage("Created", "회원삭제가 완료되었습니다.", "", "");
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.CREATED);

        }catch (Exception exception){
            ResponseMessage message = new ResponseMessage("Bad Request", "", "-1", "에러발생, 다시 시도해 주십시오.");
            exception.printStackTrace();
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> logIn(@RequestBody JwtRequest authenticationRequest) throws Exception {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        } catch (DisabledException e) {
            ResponseMessage message = new ResponseMessage("Bad Request", "", "-1", "계정이 비활성화 상태 입니다.");
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.BAD_REQUEST);
        } catch (BadCredentialsException e) {
            ResponseMessage message = new ResponseMessage("Bad Request", "", "-1", "정보가 일치하지 않습니다.");
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.BAD_REQUEST);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        ResponseMessage message = new ResponseMessage("OK", token, "", "");
        return new ResponseEntity<ResponseMessage>(message, HttpStatus.OK);
    }

    @GetMapping("/histories/{userId}")
    public List<UserHistoryResponse> getUserHistoryResponseByUserId(@PathVariable String userId) {
        return userService.getUserHistoryResponseByUserId(userId);
    }

    @PostMapping("/histories")
    public ResponseEntity<ResponseMessage> createResult(@RequestBody UserHistoryRequest userHistoryRequest){
        try{
            userService.insertUserHistoryInfo(userHistoryRequest);
            ResponseMessage message = new ResponseMessage("Created", "결과를 저장하였습니다.", "", "");
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.CREATED);
        }catch (Exception exception){
            ResponseMessage message = new ResponseMessage("Bad Request", "", "-1", "에러발생, 다시 시도해 주십시오.");
            exception.printStackTrace();
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/histories/{userId}/{userHistoryId}")
    public ResponseEntity<ResponseMessage> removeUserHistory(@PathVariable String userId, @PathVariable String userHistoryId){
        try{
            userService.deleteUserHistoryInfo(userHistoryId,userId);
            ResponseMessage message = new ResponseMessage("Created", "삭제하였습니다.", "", "");
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.CREATED);
        }catch (Exception exception){
            ResponseMessage message = new ResponseMessage("Bad Request", "", "-1", "에러발생, 다시 시도해 주십시오.");
            exception.printStackTrace();
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/histories/{userId}")
    public ResponseEntity<ResponseMessage> removeUserHistoryByUserId(@PathVariable String userId){
        try{
            userService.deleteUserHistoryListByUserId(userId);
            ResponseMessage message = new ResponseMessage("Created", "삭제하였습니다.", "", "");
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.CREATED);
        }catch (Exception exception){
            ResponseMessage message = new ResponseMessage("Bad Request", "", "-1", "에러발생, 다시 시도해 주십시오.");
            exception.printStackTrace();
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.BAD_REQUEST);
        }
    }

}
