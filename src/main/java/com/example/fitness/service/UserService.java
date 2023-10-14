package com.example.fitness.service;

import com.example.fitness.config.JwtUtil;
import com.example.fitness.exception.InvalidUsernameOrPasswordException;
import com.example.fitness.exception.UsernameIsExsistsException;
import com.example.fitness.model.*;
import com.example.fitness.model.request.LoginRequest;
import com.example.fitness.model.request.SignupRequest;
import com.example.fitness.model.response.LoginResponse;
import com.example.fitness.repository.GuestRepository;
import com.example.fitness.repository.TrainerRepository;
import com.example.fitness.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse authenticate(LoginRequest authRequest) throws InvalidUsernameOrPasswordException{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            String token = jwtUtil.createToken(authRequest.getUsername());
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            return response;
        } catch (AuthenticationException ex) {
            throw new InvalidUsernameOrPasswordException("Hibás felhasználónév vagy jelszó!");
        }
    }

    public String generateToken(String username) {
        return jwtUtil.createToken(username);
    }

    public User signup(SignupRequest request){
        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            //Létező felhasználónév
            throw new UsernameIsExsistsException("Ezzel a felhasználónévvel már létezik felhasználó!");
        }
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setUser(newUser);
        if(request.getRole().equals(RoleEnumType.GUEST.name())){
            Guest guest = new Guest();
            guest.setUser(newUser);
            guest.setEmail(request.getEmail());
            guest.setFirst_name(request.getFirstName());
            guest.setLast_name(request.getLastName());
            guestRepository.save(guest);
            role.setRole(RoleEnumType.GUEST);
        }else{
            Trainer trainer = new Trainer();
            trainer.setUser(newUser);
            trainer.setEmail(request.getEmail());
            trainer.setLast_name(request.getLastName());
            trainer.setFirst_name(request.getFirstName());
            trainer.setType(request.getType());
            trainerRepository.save(trainer);
            role.setRole(RoleEnumType.TRAINER);
        }
        roles.add(role);
        newUser.setRoles(roles);
        userRepository.save(newUser);
        return newUser;
    }

}
