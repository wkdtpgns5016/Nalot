package com.example.nalot.controller;

import com.example.nalot.config.JwtTokenUtil;
import com.example.nalot.model.authentication.JwtRequest;
import com.example.nalot.model.response.ResponseMessage;
import com.example.nalot.model.user.UserDto;
import com.example.nalot.model.user.UserHistoryRequest;
import com.example.nalot.model.user.UserHistoryResponse;
import com.example.nalot.service.authentication.UserDetailsServiceImpl;
import com.example.nalot.service.user.UserService;
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
            ResponseMessage message = new ResponseMessage("Created", "??????????????? ?????? ???????????????.", "", "");
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.CREATED);

        }catch (Exception exception){
            ResponseMessage message = new ResponseMessage("Bad Request", "", "-1", "????????????, ?????? ????????? ????????????.");
            exception.printStackTrace();
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.BAD_REQUEST);

        }
    }

    @PutMapping("{userId}")
    public ResponseEntity<ResponseMessage> modifyUserInfo(@PathVariable String userId, @RequestBody UserDto user) {
        try{
            int code = userService.updateUserInfo(userId, user);
            ResponseMessage message = new ResponseMessage("Created", "???????????? ????????? ?????????????????????.", "", "");
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.CREATED);

        }catch (Exception exception){
            ResponseMessage message = new ResponseMessage("Bad Request", "", "-1", "????????????, ?????? ????????? ????????????.");
            exception.printStackTrace();
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<ResponseMessage> removeUser(@PathVariable String userId) {
        try{
            int code = userService.deleteUserInfo(userId);
            ResponseMessage message = new ResponseMessage("Created", "??????????????? ?????????????????????.", "", "");
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.CREATED);

        }catch (Exception exception){
            ResponseMessage message = new ResponseMessage("Bad Request", "", "-1", "????????????, ?????? ????????? ????????????.");
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
            ResponseMessage message = new ResponseMessage("Bad Request", "", "-1", "????????? ???????????? ?????? ?????????.");
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.BAD_REQUEST);
        } catch (BadCredentialsException e) {
            ResponseMessage message = new ResponseMessage("Bad Request", "", "-1", "????????? ???????????? ????????????.");
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

    @GetMapping("/histories/{userId}/{userHistoryId}")
    public UserHistoryResponse getUserHistoryResponseByUserHistoryId(@PathVariable String userId, @PathVariable int userHistoryId) {
        return userService.getUserHistoryResponseByUserHistoryId(userId,userHistoryId);
    }

    @PostMapping("/histories")
    public ResponseEntity<ResponseMessage> createResult(@RequestBody UserHistoryRequest userHistoryRequest){
        try{
            userService.insertUserHistoryInfo(userHistoryRequest);
            ResponseMessage message = new ResponseMessage("Created", "????????? ?????????????????????.", "", "");
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.CREATED);
        }catch (Exception exception){
            ResponseMessage message = new ResponseMessage("Bad Request", "", "-1", "????????????, ?????? ????????? ????????????.");
            exception.printStackTrace();
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/histories/{userId}/{userHistoryId}")
    public ResponseEntity<ResponseMessage> removeUserHistory(@PathVariable String userId, @PathVariable int userHistoryId){
        try{
            userService.deleteUserHistoryInfo(userHistoryId,userId);
            ResponseMessage message = new ResponseMessage("Created", "?????????????????????.", "", "");
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.CREATED);
        }catch (Exception exception){
            ResponseMessage message = new ResponseMessage("Bad Request", "", "-1", "????????????, ?????? ????????? ????????????.");
            exception.printStackTrace();
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/histories/{userId}")
    public ResponseEntity<ResponseMessage> removeUserHistoryByUserId(@PathVariable String userId){
        try{
            userService.deleteUserHistoryListByUserId(userId);
            ResponseMessage message = new ResponseMessage("Created", "?????????????????????.", "", "");
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.CREATED);
        }catch (Exception exception){
            ResponseMessage message = new ResponseMessage("Bad Request", "", "-1", "????????????, ?????? ????????? ????????????.");
            exception.printStackTrace();
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.BAD_REQUEST);
        }
    }

}
