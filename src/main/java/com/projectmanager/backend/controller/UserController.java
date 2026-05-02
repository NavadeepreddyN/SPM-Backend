package com.projectmanager.backend.controller;

import com.projectmanager.backend.entity.User;
import com.projectmanager.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/students/{sectionId}")
    public ResponseEntity<List<User>> getStudentsBySection(@PathVariable String sectionId) {
        List<User> students = userRepository.findByRoleAndSectionId("STUDENT", sectionId);
        // Ensure not to leak passwords
        students.forEach(s -> s.setPasswordHash(null));
        return ResponseEntity.ok(students);
    }
}
