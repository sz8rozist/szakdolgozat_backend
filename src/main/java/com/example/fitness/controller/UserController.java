package com.example.fitness.controller;
import com.example.fitness.config.JwtUtil;
import com.example.fitness.model.User;
import com.example.fitness.model.request.LoginRequest;
import com.example.fitness.model.request.SignupRequest;
import com.example.fitness.model.response.ErrorResponse;
import com.example.fitness.model.response.LoginResponse;
import com.example.fitness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping
    public String valami(){
        return "HELLo!";
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest request){
        try {
            Authentication authentication = userService.authenticate(request);
            String username = authentication.getName();
            User user = new User();
            user.setUsername(username);
            String token = userService.generateToken(user);
            LoginResponse loginRes = new LoginResponse(user, token);

            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/register")
    public ResponseEntity signup(@RequestBody SignupRequest request){
        return null;
    }
}
