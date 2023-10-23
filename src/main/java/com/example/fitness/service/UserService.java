package com.example.fitness.service;

import com.example.fitness.config.JwtUtil;
import com.example.fitness.exception.FileIsEmptyException;
import com.example.fitness.exception.InvalidUsernameOrPasswordException;
import com.example.fitness.exception.UserExsistException;
import com.example.fitness.exception.UsernameIsExsistsException;
import com.example.fitness.model.*;
import com.example.fitness.model.request.LoginRequest;
import com.example.fitness.model.request.SignupRequest;
import com.example.fitness.model.response.LoginResponse;
import com.example.fitness.repository.GuestRepository;
import com.example.fitness.repository.TrainerRepository;
import com.example.fitness.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public User getUserByID(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new UserExsistException("A felhasználó nem található!");
        }
        return user;
    }

    public String uploadProfilePicture(MultipartFile multipartFile, int userId) throws FileIsEmptyException {
        if(multipartFile.isEmpty()){
            throw new FileIsEmptyException("A feltöltendő fájl üres.");
        }
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new UserExsistException("A felhasználó nem található.");
        }
        try{
            String fileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get("src/profilePictures/" + File.separator + fileName);
            Files.write(path, bytes);
            user.setProfilePictureName(fileName);
            userRepository.save(user);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Resource> getImageByName(String imageName) throws FileNotFoundException {
        String path = "src/profilePictures/" + imageName;
        File file = new File(path);
        if(!file.exists()){
            throw new FileNotFoundException("A fájl nem létezik.");
        }
        Resource image = new FileSystemResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename\"" + imageName + "\"")
                .body(image);

    }
}
