package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.UserDto;
import com.ecommerce.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * POST /api/v1/auth/signup
     * Registers a new user and returns JWT tokens.
     * Validation is handled by the API Gateway layer.
     */
    @PostMapping("/signup")
    public ResponseEntity<UserDto.AuthResponse> signup(@RequestBody UserDto.SignupRequest request) {
        UserDto.AuthResponse response = userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * POST /api/v1/auth/login
     * Authenticates existing user and returns JWT tokens.
     */
    @PostMapping("/login")
    public ResponseEntity<UserDto.AuthResponse> login(@RequestBody UserDto.LoginRequest request) {
        UserDto.AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/v1/auth/refresh
     * Issues new access token using a valid refresh token.
     */
    @PostMapping("/refresh")
    public ResponseEntity<UserDto.AuthResponse> refresh(@RequestBody UserDto.RefreshTokenRequest request) {
        UserDto.AuthResponse response = userService.refreshToken(request);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/v1/auth/health
     * Simple health check — useful for service discovery & gateway readiness.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("UserService is up");
    }
}
