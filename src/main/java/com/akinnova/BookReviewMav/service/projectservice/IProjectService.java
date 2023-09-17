package com.akinnova.BookReviewMav.service.projectservice;

import com.akinnova.BookReviewMav.dto.projectdto.*;
import com.akinnova.BookReviewMav.response.ResponsePojo;
import org.springframework.http.ResponseEntity;

public interface IProjectService {
    ResponsePojo<ProjectResponseDto> addProject(ProjectCreateDto projectCreateDto);
    ResponseEntity<?> findAllProjects(int pageNum, int pageSize);
    ResponseEntity<?> findProjectByOwner(String author, int pageNum, int pageSize);
//    ResponseEntity<?> findProjectByTitle(String title, int pageNum, int pageSize);
    ResponseEntity<?> findProjectByProjectId(String projectId);
    ResponseEntity<?> findPendingProjects(int pageNum, int pageSize);
    ResponseEntity<?> findStartedProjects(int pageNum, int pageSize);
    ResponseEntity<?> findCompletedProjects(int pageNum, int pageSize);
    ResponseEntity<?> updateProject(String projectId, ProjectUpdateDto projectUpdateDto);
    ResponseEntity<?> serviceProviderProjectUpdate(String projectId, ProjectServiceProviderUpdateDto serviceProviderUpdateDto);
    ResponseEntity<?> adminProjectUpdate(String projectId, ProjectAdminUpdateDto adminUpdateDto);
    ResponseEntity<?> deleteProject(String projectId);
    //ResponseEntity<?> searchProject(String author, String title, String projectId, int pageNum, int pageSize);

}
