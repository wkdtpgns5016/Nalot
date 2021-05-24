package com.example.nalot.controller;

import com.example.nalot.model.ResponseMessage;
import com.example.nalot.model.UserDto;
import com.example.nalot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
            System.out.println(code);
            ResponseMessage message = new ResponseMessage("Created", "회원가입이 완료 되었습니다.", "", "");
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.CREATED);

        }catch (Exception exception){
            ResponseMessage message = new ResponseMessage("Bad Request", "", "-1", "에러발생, 다시 시도해 주십시오.");
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("")
    public ResponseEntity<ResponseMessage> modifyUserInfo(@RequestBody UserDto user) {
        try{
            int code = userService.updateUserInfo(user);
            System.out.println(code);
            ResponseMessage message = new ResponseMessage("Created", "회원정보 수정이 완료되었습니다.", "", "");
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.CREATED);

        }catch (Exception exception){
            ResponseMessage message = new ResponseMessage("Bad Request", "", "-1", "에러발생, 다시 시도해 주십시오.");
            exception.printStackTrace();
            return new ResponseEntity<ResponseMessage>(message, HttpStatus.BAD_REQUEST);
        }
    }
}
