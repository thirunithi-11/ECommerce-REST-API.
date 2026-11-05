
package com.ecommerce.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.ecommerce.dto.UserRegistrationRequest;
import com.ecommerce.dto.UserUpdateRequest;
import com.ecommerce.dto.UserResponse;
import com.ecommerce.entity.User;
import com.ecommerce.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return convertToResponse(user);
    }

    // Register new user
    @PostMapping
    public UserResponse registerUser(
            @Valid @RequestBody UserRegistrationRequest request) {

        User user = userService.registerUser(request);
        return convertToResponse(user);
    }

    // Update existing user
    @PutMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {

        User user = userService.updateUser(id, request);
        return convertToResponse(user);
    }

    // Deactivate user account (Soft Delete)
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return "User deleted successfully";
    }

    // Convert User entity into safe response DTO
    private UserResponse convertToResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.isEnabled()
        );
    }
}


    