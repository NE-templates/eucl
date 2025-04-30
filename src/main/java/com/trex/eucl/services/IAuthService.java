package com.trex.eucl.services;

import com.trex.eucl.entities.User;
import com.trex.eucl.request.LoginRequest;
import com.trex.eucl.request.RegisterRequest;
import com.trex.eucl.response.AuthResponse;

import java.util.UUID;

public interface IAuthService {

    AuthResponse login(LoginRequest loginRequest);
    User register(RegisterRequest registerRequest);
    void resetPassword(UUID id, String newPassword);

}