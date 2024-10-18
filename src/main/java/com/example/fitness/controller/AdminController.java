package com.example.fitness.controller;

import com.example.fitness.model.dto.AdminUsersDTO;
import com.example.fitness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
@CrossOrigin(value = "*",maxAge = 0)
//@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public AdminUsersDTO getAllTrainers() {
        return userService.getAllUserForAdmin();
    }
}
