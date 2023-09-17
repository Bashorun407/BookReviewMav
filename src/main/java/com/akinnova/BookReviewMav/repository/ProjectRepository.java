package com.akinnova.BookReviewMav.repository;

import com.akinnova.BookReviewMav.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<List<Project>> findByTitle(String title);
    Optional<List<Project>> findByClientUsername(String username);
    //Optional<List<Project>> findByServiceProviderUsername(String username);
    Optional<Project> findByProjectId(String projectId);
}
