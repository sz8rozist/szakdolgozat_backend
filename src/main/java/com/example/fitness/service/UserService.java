package com.example.fitness.service;

import com.example.fitness.config.JwtUtil;
import com.example.fitness.exception.*;
import com.example.fitness.model.*;
import com.example.fitness.model.dto.UserDto;
import com.example.fitness.model.request.CheckPasswordRequest;
import com.example.fitness.model.request.LoginRequest;
import com.example.fitness.model.request.SignupRequest;
import com.example.fitness.model.request.UpdateProfileRequest;
import com.example.fitness.model.dto.LoginDto;
import com.example.fitness.repository.GuestRepository;
import com.example.fitness.repository.MessageRepository;
import com.example.fitness.repository.TrainerRepository;
import com.example.fitness.repository.UserRepository;
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
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final GuestRepository guestRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MessageRepository messageRepository;

    public UserService(AuthenticationManager authenticationManager, UserRepository userRepository, TrainerRepository trainerRepository, GuestRepository guestRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, MessageRepository messageRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
        this.guestRepository = guestRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.messageRepository = messageRepository;
    }

    public LoginDto authenticate(LoginRequest authRequest) throws InvalidUsernameOrPasswordException{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            String token = jwtUtil.createToken(authRequest.getUsername());
            LoginDto response = new LoginDto();
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
            guest.setGender(request.getGender().equals("1"));
            guestRepository.save(guest);
            role.setRole(RoleEnumType.GUEST);
        }else{
            Trainer trainer = new Trainer();
            trainer.setUser(newUser);
            trainer.setEmail(request.getEmail());
            trainer.setLast_name(request.getLastName());
            trainer.setFirst_name(request.getFirstName());
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
            if(user.getProfilePictureName() != null){
                Path previousProfilePicturePath = Paths.get("src/profilePictures/" + File.separator + user.getProfilePictureName());
                if(Files.exists(previousProfilePicturePath)){
                    Files.delete(previousProfilePicturePath);
                }
            }
            String fileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
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
            throw new FileNotFoundException("A fájl nem található.");
        }
        Resource image = new FileSystemResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename\"" + imageName + "\"")
                .body(image);

    }

    public void deleteProfileImage(Integer userId) throws FileNotFoundException {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new UserExsistException("A felhasználó nem található.");
        }
        String path = "src/profilePictures/" + user.getProfilePictureName();
        File file = new File(path);
        if(!file.exists()){
            throw new FileNotFoundException("A fájl nem található.");
        }
        if(file.delete()){
            user.setProfilePictureName(null);
            userRepository.save(user);
        }
    }

    public void update(UpdateProfileRequest updateProfileRequest, int userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new UserExsistException("A felhasználó nem található.");
        }
        user.getGuest().ifPresent(guest -> {
            guest.setLast_name(updateProfileRequest.getLastName());
            guest.setFirst_name(updateProfileRequest.getFirstName());
            guest.setEmail(updateProfileRequest.getEmail());
            guest.setAge(updateProfileRequest.getAge());
            guest.setWeight(updateProfileRequest.getWeight());
            guest.setHeight(updateProfileRequest.getHeight());
            guest.setGender(updateProfileRequest.getGender().equals("1"));
        });
        user.getTrainer().ifPresent(trainer ->{
            trainer.setLast_name(updateProfileRequest.getLastName());
            trainer.setFirst_name(updateProfileRequest.getFirstName());
            trainer.setEmail(updateProfileRequest.getEmail());
            trainer.setType(updateProfileRequest.getType());
        });
        userRepository.save(user);
    }

    public boolean checkPassword(int userId, CheckPasswordRequest checkPasswordRequest) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new UserExsistException("A felhasználó nem található.");
        }
        return passwordEncoder.matches(checkPasswordRequest.getPassword(), user.getPassword());
    }

    public void changePassword(CheckPasswordRequest checkPasswordRequest, int userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new UserExsistException("A felhasználó nem található.");
        }
        user.setPassword(passwordEncoder.encode(checkPasswordRequest.getPassword()));
        userRepository.save(user);
    }

    public List<UserDto> getAllUser(Integer userId) {
        List<User> userList = userRepository.findAllExceptUser(userId);
        List<UserDto> userDTOs = new ArrayList<>();
        for(User u: userList){
            UserDto userDTO = new UserDto();
            userDTO.setId(u.getId());
            userDTO.setUsername(u.getUsername());
            userDTO.setProfilePictureName(u.getProfilePictureName());
            if (u.getTrainer().isPresent()) {
                userDTO.setFirstName(u.getTrainer().get().getFirst_name());
                userDTO.setLastName(u.getTrainer().get().getLast_name());
            }
            if (u.getGuest().isPresent()) {
                userDTO.setFirstName(u.getGuest().get().getFirst_name());
                userDTO.setLastName(u.getGuest().get().getLast_name());
            }
            Message message = messageRepository.getLastMessage(userId, u.getId());
            userDTO.setLastMessage(message != null ? message.getMessage() : "");
            userDTO.setLastMessageId(message != null ? String.valueOf(message.getId()) : "");
            userDTO.setOnline(u.isOnline());
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    public void setOnline(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Nem található felhasználó!"));
        user.setOnline(true);
        userRepository.save(user);
    }

    public void removeOnline(int userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Nem található felhasználó!"));
        user.setOnline(false);
        System.out.println(user.isOnline());
        userRepository.save(user);
    }
}
