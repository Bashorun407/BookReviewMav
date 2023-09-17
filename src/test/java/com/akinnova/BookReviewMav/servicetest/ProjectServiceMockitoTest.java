package com.akinnova.BookReviewMav.servicetest;

import com.akinnova.BookReviewMav.dto.projectdto.*;
import com.akinnova.BookReviewMav.email.emailservice.EmailServiceImpl;
import com.akinnova.BookReviewMav.entity.Project;
import com.akinnova.BookReviewMav.entity.UserEntity;
import com.akinnova.BookReviewMav.enums.*;
import com.akinnova.BookReviewMav.repository.ProjectRepository;
import com.akinnova.BookReviewMav.repository.UserRepository;
import com.akinnova.BookReviewMav.response.ResponsePojo;
import com.akinnova.BookReviewMav.response.ResponseUtils;
import com.akinnova.BookReviewMav.service.projectservice.ProjectServiceImpl;
import com.akinnova.BookReviewMav.utilities.Utility;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.akinnova.BookReviewMav.enums.ApplicationStatus.NOT_SENT;
import static com.akinnova.BookReviewMav.enums.ApplicationStatus.SENT;
import static com.akinnova.BookReviewMav.enums.ProjectCompletion.*;
import static com.akinnova.BookReviewMav.enums.ProjectLevelApproval.NOT_SATISFIED;
import static com.akinnova.BookReviewMav.enums.ProjectStartApproval.NOT_APPROVED;
import static com.akinnova.BookReviewMav.enums.ApplicationReviewStatus.CONFIRMED;
import static com.akinnova.BookReviewMav.enums.ApplicationReviewStatus.NOT_CONFIRMED;
import static com.akinnova.BookReviewMav.enums.UserRole.REGULAR_USER;
import static com.akinnova.BookReviewMav.enums.UserType.CLIENT;
import static com.akinnova.BookReviewMav.enums.UserType.SERVICE_PROVIDER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.class)
@SpringBootTest(classes = {ProjectServiceMockitoTest.class})
public class ProjectServiceMockitoTest {

    @Mock
    ProjectRepository projectRepository;
    @Mock
    EmailServiceImpl emailService;
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    ProjectServiceImpl projectService;

    @Test
    @Order(1)
    public void test_addProject(){

        ProjectCreateDto projectCreateDto = ProjectCreateDto.builder()
                .coverImage("www.image.com")
                .title("Peanut Machine")
                .clientUsername("OluDot")
                .content("Peanut processing machine powered by solar")
                .build();

        Project project = Project.builder()
                .coverImage(projectCreateDto.getCoverImage())
                .title(projectCreateDto.getTitle())
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername(projectCreateDto.getClientUsername())
                .content(projectCreateDto.getContent())
                .projectId(Utility.generateBookSerialNumber(10, projectCreateDto.getTitle()))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build();


        //This will act as user repository
        List<UserEntity> userList = new ArrayList<>();

        userList.add(UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Ade")
                .userId(Utility.generateUniqueIdentifier(10, "Jade"))
                .username("Jade")
                .email("jade@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .userRole(REGULAR_USER)
                .userType(CLIENT)
                .specialization(ServiceProviderSpecialization.NONE)
                .applicationStatus(NOT_SENT)
                .reviewStatus(NOT_CONFIRMED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        userList.add(UserEntity.builder()
                .id(2L)
                .firstName("Ola")
                .lastName("Ade")
                .userId(Utility.generateUniqueIdentifier(10, "Olad"))
                .username("Olad")
                .email("olad@gmail.com")
                .password(passwordEncoder.encode("4567"))
                .userRole(REGULAR_USER)
                .userType(SERVICE_PROVIDER)
                .specialization(ServiceProviderSpecialization.EDITOR)
                .applicationStatus(SENT)
                .reviewStatus(CONFIRMED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        String username = userList.get(1).getUsername();

        //Dto that will be passed to the addProject method
        ProjectResponseDto projectResponseDto = new ProjectResponseDto(project);
        //Expected result
        ResponsePojo<ProjectResponseDto> responsePojo = new ResponsePojo<>(ResponseType.SUCCESS, ResponseUtils.PROJECT_CREATION_MESSAGE, projectResponseDto);
        when(projectRepository.save(project)).thenReturn(project);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userList.get(1)));
        //doNothing().when(emailService).sendSimpleEmail(any()); // Mock emailService call

        assertEquals(responsePojo, projectService.addProject(projectCreateDto));
    }

    @Test
    @Order(2)
    public void test_findAllProjects(){
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        List<ProjectResponseDto> projectResponseDtos = projectList.stream().map(ProjectResponseDto::new).collect(Collectors.toList());
        ResponseEntity<ResponsePojo<List<ProjectResponseDto>>> allProjects = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All projects", projectResponseDtos));

        when(projectRepository.findAll()).thenReturn(projectList);
        assertEquals(allProjects, projectService.findAllProjects(1, 2));
    }

    @Test
    @Order(3)
    public void test_findProjectByOwner(){
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        List<Project> projects = projectList.stream().filter(x-> x.getClientUsername().equals("BashOlu")).collect(Collectors.toList());

        List<ProjectResponseDto> projectResponseDtos = projectList.stream().filter(x -> x.getClientUsername().equals("BashOlu")).map(ProjectResponseDto::new).collect(Collectors.toList());
        ResponseEntity<ResponsePojo<List<ProjectResponseDto>>> expectedResult = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Projects by username", projectResponseDtos));

        when(projectRepository.findByClientUsername("BashOlu")).thenReturn(Optional.of(projects));
        assertEquals(expectedResult, projectService.findProjectByOwner("BashOlu", 1, 2));
    }

//    @Test
//    @Order(4)
//    public void test_projectByTitle(){
//        List<Project> projectList = new ArrayList<>();
//
//        projectList.add(Project.builder()
//                .id(1L)
//                .coverImage("Project")
//                .title("ProjectTitle")
//                .category(Category.ACADEMIC_PROJECT)
//                .clientUsername("OluDot")
//                .content("Project content")
//                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
//                .projectStartApproval(NOT_APPROVED)
//                .projectLevelApproval(NOT_SATISFIED)
//                .projectCompletion(PENDING)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
//        projectList.add(Project.builder()
//                .id(2L)
//                .coverImage("Research")
//                .title("ResearchTitle")
//                .category(Category.BOOK_WRITING)
//                .clientUsername("BashOlu")
//                .content("Research content")
//                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
//                .projectStartApproval(NOT_APPROVED)
//                .projectLevelApproval(NOT_SATISFIED)
//                .projectCompletion(PENDING)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
//        List<Project> projects = projectList.stream().filter(x-> x.getTitle().equals("ProjectTitle")).collect(Collectors.toList());
//
//        List<ProjectResponseDto> projectResponseDtos = projectList.stream().filter(x -> x.getTitle().equals("ProjectTitle")).map(ProjectResponseDto::new).collect(Collectors.toList());
//        ResponseEntity<ResponsePojo<List<ProjectResponseDto>>> expectedResult = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All projects by title", projectResponseDtos));
//
//        when(projectRepository.findByTitle("ProjectTitle")).thenReturn(Optional.of(projects));
//        assertEquals(expectedResult, projectService.findProjectByTitle("ProjectTitle", 1, 2));
//    }

    @Test
    @Order(5)
    public void test_findProjectByProjectId(){
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        String projectId = projectList.get(1).getProjectId();

        //This will act as user repository
        List<UserEntity> userList = new ArrayList<>();

        userList.add(UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Ade")
                .userId(Utility.generateUniqueIdentifier(10, "Jade"))
                .username("Jade")
                .email("jade@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .userRole(REGULAR_USER)
                .userType(CLIENT)
                .specialization(ServiceProviderSpecialization.NONE)
                .applicationStatus(NOT_SENT)
                .reviewStatus(NOT_CONFIRMED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        userList.add(UserEntity.builder()
                .id(2L)
                .firstName("Bashorun")
                .lastName("OLu")
                .userId(Utility.generateUniqueIdentifier(10, "Olad"))
                .username("BashOlu")
                .email("bash@gmail.com")
                .password(passwordEncoder.encode("4567"))
                .userRole(REGULAR_USER)
                .userType(SERVICE_PROVIDER)
                .specialization(ServiceProviderSpecialization.EDITOR)
                .applicationStatus(SENT)
                .reviewStatus(CONFIRMED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        String username = userList.get(1).getUsername();
        //Project to look for
        //Project project = projectRepository.findByProjectId(projectId).orElseThrow();

        ResponseEntity<ResponsePojo<Object>> expectedResult = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, String.format(ResponseUtils.FOUND_MESSAGE, projectId), new ProjectResponseDto(projectList.get(1))));

        when(projectRepository.findByProjectId(projectId)).thenReturn(Optional.of(projectList.get(1)));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userList.get(1)));
        assertEquals(expectedResult, projectService.findProjectByProjectId(projectId));
    }

    @Test
    @Order(5)
    public void test_findPendingProjects(){
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        //List<Project> projects = projectList.stream().filter(x-> x.getProjectCompletion() == PENDING).collect(Collectors.toList());

        List<ProjectResponseDto> projectResponseDtos = projectList.stream().filter(x -> x.getProjectCompletion() == PENDING).map(ProjectResponseDto::new).collect(Collectors.toList());
        ResponseEntity<ResponsePojo<List<ProjectResponseDto>>> expectedResult = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Pending projects", projectResponseDtos));

        when(projectRepository.findAll()).thenReturn(projectList);
        assertEquals(expectedResult, projectService.findPendingProjects(1, 2));
    }

    @Test
    @Order(6)
    public void test_findStartedProjects(){
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(STARTED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        //List<Project> projects = projectList.stream().filter(x-> x.getTitle().equals("ProjectTitle")).collect(Collectors.toList());

        List<ProjectResponseDto> projectResponseDtos = projectList.stream().filter(x-> x.getProjectCompletion() == STARTED).map(ProjectResponseDto::new).collect(Collectors.toList());
        ResponseEntity<ResponsePojo<List<ProjectResponseDto>>> expectedResult = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Started projects", projectResponseDtos));

        when(projectRepository.findAll()).thenReturn(projectList);
        assertEquals(expectedResult, projectService.findStartedProjects(1, 2));
    }

    @Test
    @Order(7)
    public void test_updateProject(){
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(COMPLETED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        String projectId = projectList.get(1).getProjectId();

        //This will act as user repository
        List<UserEntity> userList = new ArrayList<>();

        userList.add(UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Ade")
                .userId(Utility.generateUniqueIdentifier(10, "Jade"))
                .username("Jade")
                .email("jade@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .userRole(REGULAR_USER)
                .userType(CLIENT)
                .specialization(ServiceProviderSpecialization.NONE)
                .applicationStatus(NOT_SENT)
                .reviewStatus(NOT_CONFIRMED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        userList.add(UserEntity.builder()
                .id(2L)
                .firstName("Bashorun")
                .lastName("OLu")
                .userId(Utility.generateUniqueIdentifier(10, "Olad"))
                .username("BashOlu")
                .email("bash@gmail.com")
                .password(passwordEncoder.encode("4567"))
                .userRole(REGULAR_USER)
                .userType(SERVICE_PROVIDER)
                .specialization(ServiceProviderSpecialization.EDITOR)
                .applicationStatus(SENT)
                .reviewStatus(CONFIRMED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        String username = userList.get(1).getUsername();

        //Project project = projectRepository.findByProjectId(projectId).orElseThrow();

        //Dto that will be used to update project
        ProjectUpdateDto projectUpdateDto = ProjectUpdateDto.builder()
                .coverImage("www.image.com")
                .title("New Title")
                .content("This is the new content of the updated project")
//                .("BashOlu")
//                .projectId(projectId)
                .activeStatus(true)
                .build();

        ResponseEntity<String> expectedResult = ResponseEntity.ok(ResponseUtils.PROJECT_UPDATE_SUCCESSFUL);

        when(projectRepository.findByProjectId(projectId)).thenReturn(Optional.of(projectList.get(1)));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userList.get(1)));
        //doNothing().when(emailService.sendSimpleEmail(any()));
        assertEquals(expectedResult, projectService.updateProject(projectId, projectUpdateDto));
    }

    @Test
    @Order(8)
    public void test_serviceProviderProjectUpdate(){
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(COMPLETED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        String projectId = projectList.get(1).getProjectId();

        //This will act as user repository
        List<UserEntity> userList = new ArrayList<>();

        userList.add(UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Ade")
                .userId(Utility.generateUniqueIdentifier(10, "Jade"))
                .username("Jade")
                .email("jade@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .userRole(REGULAR_USER)
                .userType(CLIENT)
                .specialization(ServiceProviderSpecialization.NONE)
                .applicationStatus(NOT_SENT)
                .reviewStatus(NOT_CONFIRMED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        userList.add(UserEntity.builder()
                .id(2L)
                .firstName("Bashorun")
                .lastName("OLu")
                .userId(Utility.generateUniqueIdentifier(10, "Olad"))
                .username("BashOlu")
                .email("bash@gmail.com")
                .password(passwordEncoder.encode("4567"))
                .userRole(REGULAR_USER)
                .userType(SERVICE_PROVIDER)
                .specialization(ServiceProviderSpecialization.EDITOR)
                .applicationStatus(SENT)
                .reviewStatus(CONFIRMED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        String username = userList.get(1).getUsername();

       // Project project = projectRepository.findByProjectId(projectId).orElseThrow();

        //Dto that will be used to update project
        ProjectServiceProviderUpdateDto projectUpdateDto = ProjectServiceProviderUpdateDto.builder()
                //.projectId(projectId)
                .serviceProviderUsername("BashOlu")
                .projectCompletion(PENDING)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.of(2023, 10, 18, 18, 30 ))
                .build();

        ResponseEntity<String> expectedResult = ResponseEntity.ok(ResponseUtils.PROJECT_UPDATE_SUCCESSFUL);

        when(projectRepository.findByProjectId(projectId)).thenReturn(Optional.of(projectList.get(1)));
        when(projectRepository.save(projectList.get(1))).thenReturn(projectList.get(1));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userList.get(1)));
        //doNothing().when(emailService.sendSimpleEmail(any()));
        assertEquals(expectedResult, projectService.serviceProviderProjectUpdate(projectId, projectUpdateDto));
    }

    @Test
    @Order(9)
    public void test_adminProjectUpdate(){
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(COMPLETED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        String projectId = projectList.get(0).getProjectId();

        //Project project = projectRepository.findByProjectId(projectId).orElseThrow();

        //Dto that will be used to update project
        ProjectAdminUpdateDto projectUpdateDto = ProjectAdminUpdateDto.builder()
                //.projectId(projectId)
                .projectLevelApproval(ProjectLevelApproval.SATISFIED)
                .build();

        ResponseEntity<String> expectedResult = ResponseEntity.ok(ResponseUtils.PROJECT_UPDATE_SUCCESSFUL);

        when(projectRepository.findByProjectId(projectId)).thenReturn(Optional.of(projectList.get(0)));
        when(projectRepository.save(projectList.get(0))).thenReturn(projectList.get(0));
        //doNothing().when(emailService.sendSimpleEmail(any()));
        assertEquals(expectedResult, projectService.adminProjectUpdate(projectId, projectUpdateDto));
    }

    @Test
    @Order(7)
    public void test_findCompletedProjects(){
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(COMPLETED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        //List<Project> projects = projectList.stream().filter(x-> x.getTitle().equals("ProjectTitle")).collect(Collectors.toList());

        List<ProjectResponseDto> projectResponseDtos = projectList.stream().filter(x-> x.getProjectCompletion() == COMPLETED).map(ProjectResponseDto::new).collect(Collectors.toList());
        ResponseEntity<ResponsePojo<List<ProjectResponseDto>>> expectedResult = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Completed projects", projectResponseDtos));

        when(projectRepository.findAll()).thenReturn(projectList);
        assertEquals(expectedResult, projectService.findCompletedProjects(1, 2));
    }


    @Test
    @Order(8)
    public void test_deleteProject(){
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(COMPLETED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        //Determing the string by which to select the project to delete
        String projectId = projectList.get(1).getProjectId();

        //Project project = projectRepository.findByProjectId(projectId).filter(Project::getActiveStatus).orElseThrow();

        when(projectRepository.findByProjectId(projectId)).thenReturn(Optional.of(projectList.get(1)));
        assertEquals(ResponseEntity.ok(ResponseUtils.PROJECT_DELETED), projectService.deleteProject(projectId));

    }
}
