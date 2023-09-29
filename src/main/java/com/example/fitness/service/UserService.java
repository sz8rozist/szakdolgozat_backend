package com.example.fitness.service;

import com.example.fitness.config.JwtUtil;
import com.example.fitness.model.User;
import com.example.fitness.model.request.LoginRequest;
import com.example.fitness.model.request.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    public Authentication authenticate(LoginRequest request){
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
    }

    public String generateToken(User user) {
        return jwtUtil.createToken(user);
    }

    /*public User signup(SignupRequest request){

    }*/

}
