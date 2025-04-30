package com.trex.eucl.services.impl;

import com.trex.eucl.entities.User;
import com.trex.eucl.exceptions.BadRequestException;
import com.trex.eucl.exceptions.UnAuthorizedException;
import com.trex.eucl.repository.UserRepository;
import com.trex.eucl.request.LoginRequest;
import com.trex.eucl.request.RegisterRequest;
import com.trex.eucl.response.AuthResponse;
import com.trex.eucl.security.service.JwtService;
import com.trex.eucl.security.service.UserDetailsServiceImpl;
import com.trex.eucl.services.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                () -> new UnAuthorizedException("Invalid email or password")
        );

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new UnAuthorizedException("Invalid email or password");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", userDetails.getUsername());
        String token = jwtService.generateToken(claims, userDetails);

        return new AuthResponse(token, user);
    }

    @Override
    public User register(RegisterRequest registerRequest) {

        Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());

        if(existingUser.isPresent()) {
            throw new BadRequestException( String.format("User with email %s already exists", registerRequest.getEmail()));
        }

        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
        User user = User.builder()
                .email(registerRequest.getEmail())
                .names(registerRequest.getNames())
                .password(hashedPassword)
                .phone(registerRequest.getPhone())
                .nationalId(registerRequest.getNationalId())
                .role(registerRequest.getRole())
                .build();

        return userRepository.save(user);
    }

    @Override
    public void resetPassword(UUID id, String newPassword) {

    }
}
