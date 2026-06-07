package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.UserDto;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserDto.AuthResponse signup(UserDto.SignupRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already registered");
        }
        User user = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phone(request.phone())
                .address(request.address())
                .role(User.Role.CUSTOMER)
                .createdAt(Instant.now())
                .build();
        User saved = userRepository.save(user);
        return new UserDto.AuthResponse(jwtTokenProvider.createToken(saved), toResponse(saved));
    }

    public UserDto.AuthResponse login(UserDto.LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return new UserDto.AuthResponse(jwtTokenProvider.createToken(user), toResponse(user));
    }

    public UserDto.UserResponse getProfile(Long userId) {
        return userRepository.findById(userId).map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Transactional
    public UserDto.UserResponse updateProfile(Long userId, UserDto.ProfileUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setFullName(request.fullName());
        user.setPhone(request.phone());
        user.setAddress(request.address());
        return toResponse(userRepository.save(user));
    }

    private UserDto.UserResponse toResponse(User user) {
        return new UserDto.UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getPhone(),
                user.getAddress(), user.getRole(), user.getCreatedAt());
    }
}
