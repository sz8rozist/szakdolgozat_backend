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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@CrossOrigin(value = "*",maxAge = 0)
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest authRequest){
       return userService.authenticate(authRequest);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User signup(@Valid @RequestBody SignupRequest request){
       return userService.signup(request);
    }

    @GetMapping("/{userId}")
    public User getUserByID(@PathVariable Integer userId){
        return userService.getUserByID(userId);
    }

    @PostMapping("/image/{userId}")
    public String uploadProfilePicture(@RequestPart("file")MultipartFile multipartFile, @PathVariable Integer userId){
        return userService.uploadProfilePicture(multipartFile, userId);
    }

}
