
package com.ecommerce.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dto.UserRegistrationRequest;
import com.ecommerce.dto.UserUpdateRequest;
import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Get user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Check whether email exists
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    // Register new user
    @Transactional
    public User registerUser(UserRegistrationRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setPhoneNumber(request.getPhoneNumber());
        user.setEnabled(true);

        return userRepository.save(user);
    }

    // Update existing user
    @Transactional
    public User updateUser(Long id, UserUpdateRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser != null && !existingUser.getId().equals(id)) {
            throw new RuntimeException("Email already exists");
        }

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        return userRepository.save(user);
    }

    // Delete user (Soft Delete / Account Deactivation)
    @Transactional
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEnabled(false);

        userRepository.save(user);
    }
}
