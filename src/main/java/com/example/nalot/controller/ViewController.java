package com.example.nalot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Nalot")
public class ViewController {

    @GetMapping("")
    public String mainView(){ return "/Nalot"; }

    @GetMapping("/SignUp")
    public String signUpView(){ return "/Nalot/SignUp"; }
}
