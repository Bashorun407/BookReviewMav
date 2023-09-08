package com.akinnova.BookReviewMav.controller;

import com.akinnova.BookReviewMav.dto.projectdto.*;
import com.akinnova.BookReviewMav.response.ResponsePojo;
import com.akinnova.BookReviewMav.service.projectservice.ProjectServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/project/auth")

public class ProjectController {

    private final ProjectServiceImpl projectService;
    public ProjectController(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/addProject")
    public ResponsePojo<ProjectResponseDto> addProject(@RequestBody ProjectCreateDto projectCreateDto) {
        return projectService.addProject(projectCreateDto);
    }
    @GetMapping("/allProjects")
    public ResponseEntity<?> findAllProjects(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return projectService.findAllProjects(pageNum, pageSize);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> findProjectByOwner(@PathVariable(name = "username") String username, @RequestParam(defaultValue = "1") int pageNum,
                                              @RequestParam(defaultValue = "10") int pageSize) {
        return projectService.findProjectByOwner(username, pageNum, pageSize);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<?> findProjectByTitle(@PathVariable String title, @RequestParam(defaultValue = "1") int pageNum,
                                             @RequestParam(defaultValue = "10") int pageSize) {
        return projectService.findProjectByTitle(title, pageNum, pageSize);
    }

    @GetMapping("/projectId/{projectId}")
    public ResponseEntity<?> findProjectByProjectId(@PathVariable String projectId) {
        return projectService.findProjectByProjectId(projectId);
    }

    @GetMapping("/pending")
    public ResponseEntity<?> findPendingProjects(@RequestParam(defaultValue = "1") int pageNum,
                                                   @RequestParam(defaultValue = "10") int pageSize) {
        return projectService.findPendingProjects(pageNum, pageSize);
    }

    @GetMapping("/started")
    public ResponseEntity<?> findStartedProjects(@RequestParam(defaultValue = "1") int pageNum,
                                                   @RequestParam(defaultValue = "10") int pageSize) {
        return projectService.findStartedProjects(pageNum, pageSize);
    }

    @GetMapping("/completed")
    public ResponseEntity<?> findCompletedProjects(@RequestParam(defaultValue = "1") int pageNum,
                                                     @RequestParam(defaultValue = "10") int pageSize) {
        return projectService.findCompletedProjects(pageNum, pageSize);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProject(@RequestBody ProjectUpdateDto projectUpdateDto) {
        return projectService.updateProject(projectUpdateDto);
    }

    @PutMapping("/serviceProviderUpdate")
    public ResponseEntity<?> serviceProviderProjectUpdate(@RequestBody ProjectServiceProviderUpdateDto serviceProviderUpdateDto) {
        return projectService.serviceProviderProjectUpdate(serviceProviderUpdateDto);
    }

    @PutMapping("/adminUpdate")
    public ResponseEntity<?> adminProjectUpdate(@RequestBody ProjectAdminUpdateDto adminUpdateDto) {
        return projectService.adminProjectUpdate(adminUpdateDto);
    }
    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId) {
        return projectService.deleteProject(projectId);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProject(@RequestParam(required = false) String username,
                                        @RequestParam(required = false) String title,
                                        @RequestParam(required = false) String projectId,
                                        @RequestParam(defaultValue = "1") int pageNum,
                                        @RequestParam(defaultValue = "10") int pageSize) {
        return projectService.searchProject(username, title, projectId, pageNum, pageSize);
    }
}
