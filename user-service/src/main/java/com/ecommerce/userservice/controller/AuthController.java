package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.UserDto;
import com.ecommerce.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/api/v1/auth/signup")
    public ResponseEntity<UserDto.AuthResponse> signup(@RequestBody UserDto.SignupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signup(request));
    }

    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<UserDto.AuthResponse> login(@RequestBody UserDto.LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/api/v1/users/{userId}")
    public ResponseEntity<UserDto.UserResponse> profile(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getProfile(userId));
    }

    @PutMapping("/api/v1/users/{userId}")
    public ResponseEntity<UserDto.UserResponse> updateProfile(@PathVariable Long userId, @RequestBody UserDto.ProfileUpdateRequest request) {
        return ResponseEntity.ok(userService.updateProfile(userId, request));
    }

    @GetMapping("/api/v1/auth/health")
    public String health() {
        return "user-service is running";
    }
}
