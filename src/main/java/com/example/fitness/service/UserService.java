package com.example.fitness.service;

import com.example.fitness.config.JwtUtil;
import com.example.fitness.exception.InvalidUsernameOrPasswordException;
import com.example.fitness.exception.UsernameIsExsistsException;
import com.example.fitness.model.Role;
import com.example.fitness.model.User;
import com.example.fitness.model.request.LoginRequest;
import com.example.fitness.model.request.SignupRequest;
import com.example.fitness.model.response.ErrorResponse;
import com.example.fitness.model.response.LoginResponse;
import com.example.fitness.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse authenticate(LoginRequest request) throws InvalidUsernameOrPasswordException{
        try {
           Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = new User();
            user.setUsername(userDetails.getUsername());
            user.setRole(Role.valueOf(userDetails.getAuthorities().iterator().next().getAuthority()));
            String token = generateToken(user);
            return new LoginResponse(userDetails, token);
        }catch (BadCredentialsException e){
            throw new InvalidUsernameOrPasswordException("Invalid username or password");
        }
    }

    public String generateToken(User user) {
        return jwtUtil.createToken(user);
    }

    public User signup(SignupRequest request){
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if(user != null){
            //Létező felhasználónév
            throw new UsernameIsExsistsException("Ezzel a felhasználónévvel már létezik felhasználó!");
        }
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(Role.valueOf(Role.GUEST.name()));
        userRepository.save(newUser);
        return newUser;
    }

}
