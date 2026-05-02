package com.projectmanager.backend.controller;

import com.projectmanager.backend.entity.Project;
import com.projectmanager.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping("/assign")
    public ResponseEntity<?> assignProject(@RequestBody Project project) {
        try {
            Project savedProject = projectRepository.save(project);
            return ResponseEntity.ok(Map.of("message", "Project assigned successfully!", "project", savedProject));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Error saving project: " + e.getMessage()));
        }
    }

    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<Project>> getProjectsBySection(@PathVariable String sectionId) {
        List<Project> projects = projectRepository.findBySectionId(sectionId);
        return ResponseEntity.ok(projects);
    }
}
