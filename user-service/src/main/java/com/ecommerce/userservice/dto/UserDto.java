package com.ecommerce.userservice.dto;

import com.ecommerce.userservice.entity.User;

import java.time.Instant;

public class UserDto {
    public record SignupRequest(String fullName, String email, String password, String phone, String address) {}
    public record LoginRequest(String email, String password) {}
    public record AuthResponse(String token, UserResponse user) {}
    public record ProfileUpdateRequest(String fullName, String phone, String address) {}
    public record UserResponse(Long id, String fullName, String email, String phone, String address, User.Role role, Instant createdAt) {}
}
