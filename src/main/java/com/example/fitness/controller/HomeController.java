package com.example.fitness.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@CrossOrigin("*")
public class HomeController {
    @GetMapping
    @PreAuthorize("hasAuthority('GUEST')")
    public String home(){
        return "HOME";
    }
}
