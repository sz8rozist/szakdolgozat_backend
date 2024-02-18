package com.example.fitness.serviceTest;

import com.example.fitness.config.JwtUtil;
import com.example.fitness.exception.FileIsEmptyException;
import com.example.fitness.exception.InvalidUsernameOrPasswordException;
import com.example.fitness.exception.UserExsistException;
import com.example.fitness.exception.UsernameIsExsistsException;
import com.example.fitness.model.Guest;
import com.example.fitness.model.Message;
import com.example.fitness.model.Trainer;
import com.example.fitness.model.User;
import com.example.fitness.model.dto.LoginDto;
import com.example.fitness.model.dto.UserDto;
import com.example.fitness.model.request.CheckPasswordRequest;
import com.example.fitness.model.request.LoginRequest;
import com.example.fitness.model.request.SignupRequest;
import com.example.fitness.model.request.UpdateProfileRequest;
import com.example.fitness.repository.GuestRepository;
import com.example.fitness.repository.MessageRepository;
import com.example.fitness.repository.TrainerRepository;
import com.example.fitness.repository.UserRepository;
import com.example.fitness.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestUserService {

    AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
    UserRepository userRepository = mock(UserRepository.class);
    TrainerRepository trainerRepository = mock(TrainerRepository.class);
    GuestRepository guestRepository = mock(GuestRepository.class);
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    MessageRepository messageRepository = mock(MessageRepository.class);
    JwtUtil jwtUtil = mock(JwtUtil.class);

    UserService userService = new UserService(authenticationManager, userRepository, trainerRepository, guestRepository, jwtUtil, passwordEncoder, messageRepository);

    @Test
    public void testAuthenticateSuccessful() throws Exception {
        // Arrange


        LoginRequest authRequest = new LoginRequest("validUsername", "validPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(jwtUtil.createToken("validUsername")).thenReturn("mockToken");

        // Act
        LoginDto result = userService.authenticate(authRequest);

        // Assert
        assertNotNull(result);
        assertEquals("mockToken", result.getToken());
    }

    @Test
    public void testAuthenticateUnsuccessful() {
        // Arrange

        LoginRequest authRequest = new LoginRequest("invalidUsername", "invalidPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Invalid credentials") {
                });

        // Act & Assert
        assertThrows(InvalidUsernameOrPasswordException.class, () -> {
            userService.authenticate(authRequest);
        });
    }

    @Test
    public void testSignupSuccessful() {
        // Arrange
        SignupRequest request = new SignupRequest("John", "Doe", "john@example.com", "newUsername", "password", "GUEST", "1");

        when(userRepository.findByUsername("newUsername")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Act
        User result = userService.signup(request);

        // Assert
        assertNotNull(result);
        assertEquals("newUsername", result.getUsername());
    }

    @Test
    public void testSignupUsernameExists() {
        // Arrange
        SignupRequest request = new SignupRequest("John", "Doe", "john@example.com", "existingUsername", "password", "GUEST", "1");

        when(userRepository.findByUsername("existingUsername")).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(UsernameIsExsistsException.class, () -> {
            userService.signup(request);
        });
    }

    @Test
    public void testGetUserByIDFound() {
        // Arrange
        Integer userId = 1;
        User expectedUser = new User();
        expectedUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        // Act
        User result = userService.getUserByID(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        // Add additional assertions based on your specific logic for found user
    }

    @Test
    public void testGetUserByIDNotFound() {
        // Arrange
        Integer userId = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserExsistException.class, () -> {
            userService.getUserByID(userId);
        });
    }

    @Test
    public void testUploadProfilePictureSuccess() throws FileIsEmptyException {
        // Arrange

        int userId = 1;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "content".getBytes());

        // Act
        String result = userService.uploadProfilePicture(file, userId);

        // Assert
        assertNotNull(result);
        assertTrue(result.endsWith("_test.jpg")); // The generated file name should end with "_test.jpg"
        // Add additional assertions based on your specific logic for successful upload
    }

    @Test
    public void testUploadProfilePictureEmptyFile() {
        // Arrange

        int userId = 1;

        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "".getBytes());

        // Act & Assert
        assertThrows(FileIsEmptyException.class, () -> {
            userService.uploadProfilePicture(file, userId);
        });
    }

    @Test
    public void testUploadProfilePictureUserNotFound() {
        // Arrange

        int userId = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "content".getBytes());

        // Act & Assert
        assertThrows(UserExsistException.class, () -> {
            userService.uploadProfilePicture(file, userId);
        });
    }

    @Test
    public void testGetImageByNameFileNotFound() {
        // Arrange
        String imageName = "nonExistentFile.jpg";

        when(userRepository.findById(anyInt())).thenReturn(Optional.of(new User()));

        // Assume the file does not exist for testing the file not found case.
        File file = mock(File.class);
        when(file.exists()).thenReturn(false);

        // Act & Assert
        assertThrows(FileNotFoundException.class, () -> {
            userService.getImageByName(imageName);
        });
    }

    @Test
    public void testDeleteProfileImageUserNotFound() {
        // Arrange
        int userId = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserExsistException.class, () -> {
            userService.deleteProfileImage(userId);
        });
    }

    @Test
    public void testDeleteProfileImageFileNotFound() {
        // Arrange
        int userId = 1;
        User user = new User();
        user.setId(userId);
        user.setProfilePictureName("nonExistentFile.jpg");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Assume the file does not exist for testing the file not found case.
        File file = mock(File.class);
        when(file.exists()).thenReturn(false);

        // Act & Assert
        assertThrows(FileNotFoundException.class, () -> {
            userService.deleteProfileImage(userId);
        });
    }


    @Test
    public void testUpdateProfileGuest() {
        // Arrange
        int userId = 1;
        User user = new User();
        user.setId(userId);
        Guest guest = new Guest();
        user.setGuest(guest);

        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
        updateProfileRequest.setFirstName("John");
        updateProfileRequest.setLastName("Doe");
        updateProfileRequest.setEmail("john.doe@example.com");
        updateProfileRequest.setAge(25);
        updateProfileRequest.setWeight(70.5F);
        updateProfileRequest.setHeight(175.0F);
        updateProfileRequest.setGender("1");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.update(updateProfileRequest, userId);

        // Assert
        assertEquals("John", guest.getFirst_name());
        assertEquals("Doe", guest.getLast_name());
        assertEquals("john.doe@example.com", guest.getEmail());
        assertEquals(25, guest.getAge());
        assertEquals(70.5, guest.getWeight());
        assertEquals(175.0, guest.getHeight());
        assertTrue(guest.isGender());
    }

    @Test
    public void testUpdateProfileTrainer() {
        // Arrange
        int userId = 1;
        User user = new User();
        user.setId(userId);
        Trainer trainer = new Trainer();
        user.setTrainer(trainer);

        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
        updateProfileRequest.setFirstName("Jane");
        updateProfileRequest.setLastName("Doe");
        updateProfileRequest.setEmail("jane.doe@example.com");
        updateProfileRequest.setType("Fitness");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.update(updateProfileRequest, userId);

        // Assert
        assertEquals("Jane", trainer.getFirst_name());
        assertEquals("Doe", trainer.getLast_name());
        assertEquals("jane.doe@example.com", trainer.getEmail());
        assertEquals("Fitness", trainer.getType());
    }

    @Test
    public void testCheckPasswordNotMatch() {
        // Arrange
        int userId = 1;
        User user = new User();
        user.setId(userId);
        user.setPassword("$2a$10$EXAMPLEHASH"); // Placeholder hash for testing

        CheckPasswordRequest checkPasswordRequest = new CheckPasswordRequest();
        checkPasswordRequest.setPassword("wrongPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        boolean result = userService.checkPassword(userId, checkPasswordRequest);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testChangePassword() {
        // Arrange
        int userId = 1;
        User user = new User();
        user.setId(userId);
        user.setPassword("$2a$10$EXAMPLEHASH"); // Placeholder hash for testing

        CheckPasswordRequest checkPasswordRequest = new CheckPasswordRequest();
        checkPasswordRequest.setPassword("newPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPassword")).thenReturn("$2a$10$NEWEXAMPLEHASH"); // Placeholder hash for testing

        // Act
        userService.changePassword(checkPasswordRequest, userId);

        // Assert
        verify(userRepository, times(1)).save(user);
        assertEquals("$2a$10$NEWEXAMPLEHASH", user.getPassword());
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        int userId = 1;
        User user = new User();
        user.setId(userId);
        user.setUsername("john_doe");
        user.setProfilePictureName("profile.jpg");

        Trainer trainer = new Trainer();
        trainer.setFirst_name("TrainerFirstName");
        trainer.setLast_name("TrainerLastName");
        user.setTrainer(trainer);

        Guest guest = new Guest();
        guest.setFirst_name("GuestFirstName");
        guest.setLast_name("GuestLastName");
        user.setGuest(guest);

        List<User> userList = Collections.singletonList(user);
        when(userRepository.findAllExceptUser(userId)).thenReturn(userList);

        // Mock last message for testing purposes
        Message lastMessage = new Message();
        lastMessage.setMessage("Last message");
        when(messageRepository.getLastMessage(userId, user.getId())).thenReturn(lastMessage);

        // Act
        List<UserDto> result = userService.getAllUser(userId);

        // Assert
        assertEquals(1, result.size());
        UserDto userDto = result.get(0);
        assertEquals(userId, userDto.getId());
        assertEquals("john_doe", userDto.getUsername());
        assertEquals("profile.jpg", userDto.getProfilePictureName());
        assertEquals("GuestFirstName", userDto.getFirstName());
        assertEquals("GuestLastName", userDto.getLastName());
        assertEquals("Last message", userDto.getLastMessage());
    }
}
