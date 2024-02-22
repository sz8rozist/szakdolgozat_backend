package com.example.fitness.controller;
import com.example.fitness.model.User;
import com.example.fitness.model.dto.UserDto;
import com.example.fitness.model.request.CheckPasswordRequest;
import com.example.fitness.model.request.LoginRequest;
import com.example.fitness.model.request.SignupRequest;
import com.example.fitness.model.request.UpdateProfileRequest;
import com.example.fitness.model.dto.LoginDto;
import com.example.fitness.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(value = "*",maxAge = 0)
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginDto login(@RequestBody LoginRequest authRequest){
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
    public void updateProfileData(@Valid @RequestBody UpdateProfileRequest updateProfileRequest, @PathVariable int userId){
        userService.update(updateProfileRequest, userId);
    }

    @PostMapping("/password/{userId}")
    public boolean checkPassword(@PathVariable int userId, @RequestBody CheckPasswordRequest checkPasswordRequest){
        return userService.checkPassword(userId, checkPasswordRequest);
    }

    @PutMapping("/password/{userId}")
    public void changePassword(@RequestBody CheckPasswordRequest checkPasswordRequest, @PathVariable int userId){
        userService.changePassword(checkPasswordRequest, userId);
    }

    @GetMapping("/userMessages/{userId}")
    public List<UserDto> getAllUser(@PathVariable Integer userId){
        return userService.getAllUser(userId);
    }

    @GetMapping("/setOnline/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void setOnline(@PathVariable int userId){
        userService.setOnline(userId);
    }
    @GetMapping("/removeOnline/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeOnline(@PathVariable int userId){
        userService.removeOnline(userId);
    }
}
