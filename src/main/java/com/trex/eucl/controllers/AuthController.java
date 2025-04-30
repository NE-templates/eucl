package com.trex.eucl.controllers;

import com.trex.eucl.entities.User;
import com.trex.eucl.request.LoginRequest;
import com.trex.eucl.request.RegisterRequest;
import com.trex.eucl.response.AuthResponse;
import com.trex.eucl.services.impl.AuthServiceImpl;
import com.trex.eucl.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        return new ApiResponse<>("User Logged in successfully", HttpStatus.OK, authResponse).toResponseEntity();
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User registeredUser = authService.register(registerRequest);
        return new ApiResponse<>("User registered successfully", HttpStatus.OK, registeredUser).toResponseEntity();
    }
}
