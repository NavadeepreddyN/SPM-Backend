package com.projectmanager.backend.controller;

import com.projectmanager.backend.entity.User;
import com.projectmanager.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> payload) {
        String uniId = payload.get("id");
        if (userRepository.existsByUniversityId(uniId)) {
            return ResponseEntity.badRequest().body(Map.of("message", "University ID already exists!"));
        }

        User user = new User();
        user.setFullName(payload.get("fullName"));
        user.setUniversityId(uniId);
        user.setEmail(payload.get("email"));
        user.setRole(payload.get("role").toUpperCase());
        user.setSectionId(payload.get("sectionId"));
        user.setPasswordHash(passwordEncoder.encode(payload.get("password")));

        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "User registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> payload) {
        String uniId = payload.get("id");
        String password = payload.get("password");
        String requestedRole = payload.get("role").toUpperCase();

        Optional<User> userOpt = userRepository.findByUniversityId(uniId);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPasswordHash())) {
                if (user.getRole().equals(requestedRole)) {
                    // Success
                    return ResponseEntity.ok(Map.of(
                        "message", "Login successful",
                        "userId", user.getId(),
                        "fullName", user.getFullName(),
                        "role", user.getRole(),
                        "sectionId", user.getSectionId()
                    ));
                } else {
                    return ResponseEntity.badRequest().body(Map.of("message", "Incorrect portal for this user role."));
                }
            }
        }
        return ResponseEntity.status(401).body(Map.of("message", "Invalid ID or Password"));
    }
}
