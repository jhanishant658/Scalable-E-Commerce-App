package com.ecommerce.userservice.dto;

import com.ecommerce.userservice.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserDto {

    // ─── Signup Request ───────────────────────────────────────────────────────
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class SignupRequest {
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private User.Role role; // optional, defaults to CUSTOMER
    }

    // ─── Login Request ────────────────────────────────────────────────────────
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class LoginRequest {
        private String email;
        private String password;
    }

    // ─── Auth Response (JWT tokens) ───────────────────────────────────────────
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class AuthResponse {
        private String accessToken;
        private String refreshToken;
        private String tokenType = "Bearer";
        private long expiresIn;        // seconds
        private UserInfo user;
    }

    // ─── User Info (safe to expose) ───────────────────────────────────────────
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class UserInfo {
        private UUID id;
        private String email;
        private String firstName;
        private String lastName;
        private User.Role role;
        private LocalDateTime createdAt;
    }

    // ─── API Error Wrapper ────────────────────────────────────────────────────
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ApiError {
        private int status;
        private String error;
        private String message;
        private LocalDateTime timestamp;
    }

    // ─── Refresh Token Request ────────────────────────────────────────────────
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class RefreshTokenRequest {
        private String refreshToken;
    }
}
