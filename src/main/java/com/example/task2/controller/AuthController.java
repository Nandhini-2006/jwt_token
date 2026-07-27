package com.example.task2.controller;

import com.example.task2.dto.AuthRequest;
import com.example.task2.dto.AuthResponse;
import com.example.task2.dto.RegisterRequest;
import com.example.task2.security.JwtService;
import com.example.task2.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {

        return authService.register(request);

    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        String token = jwtService.generateToken(request.getEmail());

        return new AuthResponse(token);
    }

    @GetMapping("/hello")
    public String hello() {

        return "JWT Authentication Successful";

    }
}