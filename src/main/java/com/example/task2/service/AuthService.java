package com.example.task2.service;

import com.example.task2.dto.AuthResponse;
import com.example.task2.dto.RegisterRequest;
import com.example.task2.entity.User;
import com.example.task2.repository.UserRepository;
import com.example.task2.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        repository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}