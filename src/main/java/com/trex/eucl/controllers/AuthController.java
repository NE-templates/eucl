package com.trex.eucl.controllers;

import com.trex.eucl.entities.User;
import com.trex.eucl.request.LoginRequest;
import com.trex.eucl.request.RegisterRequest;
import com.trex.eucl.response.AuthResponse;
import com.trex.eucl.services.impl.AuthServiceImpl;
import com.trex.eucl.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    @ApiResponse(responseCode = "200", description = "User successfully logged in")
    @PostMapping("/login")
    public ResponseEntity<APIResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        return new APIResponse<>("User Logged in successfully", HttpStatus.OK, authResponse).toResponseEntity();
    }

    @Operation(summary = "Register new user", description = "Create new user account")
    @ApiResponse(responseCode = "200", description = "User successfully registered")
    @PostMapping("/register")
    public ResponseEntity<APIResponse<User>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User registeredUser = authService.register(registerRequest);
        return new APIResponse<>("User registered successfully", HttpStatus.OK, registeredUser).toResponseEntity();
    }
}
