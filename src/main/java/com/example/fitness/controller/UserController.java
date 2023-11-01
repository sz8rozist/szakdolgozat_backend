package com.example.fitness.controller;
import com.example.fitness.model.User;
import com.example.fitness.model.request.CheckPasswordRequest;
import com.example.fitness.model.request.LoginRequest;
import com.example.fitness.model.request.SignupRequest;
import com.example.fitness.model.request.UpdateProfile;
import com.example.fitness.model.response.LoginResponse;
import com.example.fitness.model.response.UserResponse;
import com.example.fitness.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

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
    public UserResponse getUserByID(@PathVariable Integer userId){
        return userService.getUserByID(userId);
    }

    @PostMapping("/image/{userId}")
    public String uploadProfilePicture(@RequestPart("file")MultipartFile multipartFile, @PathVariable int userId){
        return userService.uploadProfilePicture(multipartFile, userId);
    }

    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> getImageByName(@PathVariable String imageName) throws FileNotFoundException {
        return userService.getImageByName(imageName);
    }

    @DeleteMapping("/image/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfileImage(@PathVariable int userId) throws FileNotFoundException {
        userService.deleteProfileImage(userId);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfileData(@Valid @RequestBody UpdateProfile updateProfile, @PathVariable int userId){
        userService.update(updateProfile, userId);
    }

    @PostMapping("/password/{userId}")
    public boolean checkPassword(@PathVariable int userId, @RequestBody CheckPasswordRequest checkPasswordRequest){
        return userService.checkPassword(userId, checkPasswordRequest);
    }

    @PutMapping("/password/{userId}")
    public void changePassword(@RequestBody CheckPasswordRequest checkPasswordRequest, @PathVariable int userId){
        userService.changePassword(checkPasswordRequest, userId);
    }

}
