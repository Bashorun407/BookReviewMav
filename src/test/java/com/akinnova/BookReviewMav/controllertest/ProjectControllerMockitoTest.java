package com.akinnova.BookReviewMav.controllertest;

import com.akinnova.BookReviewMav.controller.ProjectController;
import com.akinnova.BookReviewMav.entity.Project;
import com.akinnova.BookReviewMav.service.projectservice.ProjectServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ProjectControllerMockitoTest.class})
public class ProjectControllerMockitoTest {

    @Mock
    ProjectServiceImpl projectService;

    @InjectMocks
    ProjectController projectController;

    List<Project> projectList;

    Project project;
}
