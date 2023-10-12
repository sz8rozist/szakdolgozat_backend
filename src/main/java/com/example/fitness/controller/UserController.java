package com.example.fitness.controller;
import com.example.fitness.model.User;
import com.example.fitness.model.request.LoginRequest;
import com.example.fitness.model.request.SignupRequest;
import com.example.fitness.model.response.LoginResponse;
import com.example.fitness.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public User signup(@Valid @RequestBody SignupRequest request){
       return userService.signup(request);
    }


}
