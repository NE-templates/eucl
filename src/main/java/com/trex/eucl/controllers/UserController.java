package com.trex.eucl.controllers;

import com.trex.eucl.entities.User;
import com.trex.eucl.request.UpdateUserRequest;
import com.trex.eucl.response.APIResponse;
import com.trex.eucl.services.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "Users", description = "Endpoints for managing app users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @Operation(summary = "Get user by ID", description = "Fetches a user using UUID")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<User>> getUserById(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        return new APIResponse<>("User retrieved successfully", HttpStatus.OK, user).toResponseEntity();
    }

    @Operation(summary = "Get meter by ID", description = "Fetches a meter using UUID")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{email}")
    public ResponseEntity<APIResponse<User>> getUserByEmail( @Valid @NotBlank @Email @PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return new APIResponse<>("User retrieved successfully", HttpStatus.OK, user).toResponseEntity();
    }

    @Operation(summary = "Get all users", description = "Fetches all users")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<APIResponse<List<User>>> getAll() {
        List<User> users = userService.getAll();
        return new APIResponse<>("All users retrieved successfully", HttpStatus.OK, users).toResponseEntity();
    }

    @Operation(summary = "Update a user", description = "Api for updating a user")
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/update/{id}")
    public ResponseEntity<APIResponse<User>> updateUser(@PathVariable UUID id, @Valid UpdateUserRequest updateUserRequest) {
        User user = userService.updateUser(updateUserRequest, id);
        return new APIResponse<>("User updated successfully", HttpStatus.OK, user).toResponseEntity();
    }

    @Operation(summary = "Delete user by ID", description = "Api for deleting a user by Id")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse<User>> deleteById(@PathVariable UUID id) {
        User user = userService.deleteUser(id);
        return new APIResponse<>("User deleted successfully", HttpStatus.OK, user).toResponseEntity();
    }

}
