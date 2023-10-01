package com.example.fitness.controller;
import com.example.fitness.model.User;
import com.example.fitness.model.request.LoginRequest;
import com.example.fitness.model.request.SignupRequest;
import com.example.fitness.model.response.LoginResponse;
import com.example.fitness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping
    public String valami(){
        return "HELLo!";
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
       return userService.authenticate(request);
    }

    @PostMapping("/register")
    public User signup(@RequestBody SignupRequest request){
       return userService.signup(request);
    }
}
