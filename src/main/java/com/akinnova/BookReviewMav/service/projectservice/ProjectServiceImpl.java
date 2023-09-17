package com.akinnova.BookReviewMav.service.projectservice;

import com.akinnova.BookReviewMav.dto.projectdto.*;
import com.akinnova.BookReviewMav.email.emaildto.EmailDetail;
import com.akinnova.BookReviewMav.email.emailservice.EmailServiceImpl;
import com.akinnova.BookReviewMav.entity.Project;
import com.akinnova.BookReviewMav.entity.UserEntity;
import com.akinnova.BookReviewMav.enums.JobAcceptanceStatus;
import com.akinnova.BookReviewMav.enums.ResponseType;
import com.akinnova.BookReviewMav.exception.ApiException;
import com.akinnova.BookReviewMav.repository.ProjectRepository;
import com.akinnova.BookReviewMav.repository.UserRepository;
import com.akinnova.BookReviewMav.response.EmailResponse;
import com.akinnova.BookReviewMav.response.ResponsePojo;
import com.akinnova.BookReviewMav.response.ResponseUtils;
import com.akinnova.BookReviewMav.utilities.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.akinnova.BookReviewMav.enums.ProjectLevelApproval.NOT_SATISFIED;
import static com.akinnova.BookReviewMav.enums.ProjectStartApproval.NOT_APPROVED;
import static com.akinnova.BookReviewMav.enums.ProjectCompletion.*;

@Service
public class ProjectServiceImpl implements IProjectService {

    Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);
    private final ProjectRepository projectRepository;
    private final EmailServiceImpl emailService;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, EmailServiceImpl emailService, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @Override
    public ResponsePojo<ProjectResponseDto> addProject(ProjectCreateDto projectCreateDto) {

        //Create and save new object
        Project project =  projectRepository.save(Project.builder()
                .coverImage(projectCreateDto.getCoverImage())
                .title(projectCreateDto.getTitle())
                .category(projectCreateDto.getCategory())
                .clientUsername(projectCreateDto.getClientUsername())
                .content(projectCreateDto.getContent())
                .projectId(Utility.generateBookSerialNumber(10, projectCreateDto.getTitle()))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        logger.info("A new project has been created.");
        //Getting the details of the user
        UserEntity user = userRepository.findByUsername(project.getClientUsername())
                .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_USER_BY_USERNAME , projectCreateDto.getClientUsername())));

        //Sending email to the project owner that a new project has been created.
        EmailDetail emailDetail = EmailDetail.builder()
                .recipient(user.getEmail())
                .subject(EmailResponse.PROJECT_CREATION_SUBJECT)
                .body(String.format(EmailResponse.PROJECT_CREATION_MAIL, projectCreateDto.getTitle(), project.getProjectId()))
                .build();

        emailService.sendSimpleEmail(emailDetail);

        return new ResponsePojo<>(ResponseType.SUCCESS, ResponseUtils.PROJECT_CREATION_MESSAGE,new ProjectResponseDto(project));
    }

    @Override
    public ResponseEntity<?> findAllProjects(int pageNum, int pageSize) {

        List<Project> projectList = projectRepository.findAll();

        if (projectList.isEmpty())
            return new ResponseEntity<>(ResponseUtils.NO_PROJECTS, HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All projects", projectList.stream()
                .filter(Project::getActiveStatus).sorted(Comparator.comparing(Project::getTitle)).skip(pageNum - 1).limit(pageSize).map(ProjectResponseDto::new).collect(Collectors.toList())));
    }

    @Override
    public ResponseEntity<?> findProjectByOwner(String username, int pageNum, int pageSize) {

        List<Project> projectList = projectRepository.findByClientUsername(username)
                .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_PROJECT_BY_NAME, username)));

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Projects by username", projectList.stream()
                .filter(Project::getActiveStatus).sorted(Comparator.comparing(Project::getTitle)).skip(pageNum - 1).limit(pageSize).map(ProjectResponseDto::new).collect(Collectors.toList())));
    }

//
//    @Override
//    public ResponseEntity<?> findProjectByTitle(String title, int pageNum, int pageSize) {
//
//        List<Project> bookEntityList = projectRepository.findByTitle(title)
//                .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_PROJECT_BY_NAME, title)));
//
//        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All projects by title", bookEntityList.stream()
//                .filter(Project::getActiveStatus).sorted(Comparator.comparing(Project::getTitle)).skip(pageNum - 1).limit(pageSize).map(ProjectResponseDto::new).collect(Collectors.toList())));
//    }

    @Override
    public ResponseEntity<?> findProjectByProjectId(String projectId) {

        Project project = projectRepository.findByProjectId(projectId)
                .orElseThrow(()-> new ApiException(ResponseUtils.NO_PROJECT_BY_ID + projectId));

        return ResponseEntity
                .ok(new ResponsePojo<>(ResponseType.SUCCESS, String.format(ResponseUtils.FOUND_MESSAGE, projectId), new ProjectResponseDto(project)));
    }

    // TODO: 13/08/2023 To complete the following methods
    @Override
    public ResponseEntity<?> findPendingProjects(int pageNum, int pageSize) {
        List<Project> projectList = projectRepository.findAll().stream()
                .filter(Project::getActiveStatus)
                .filter(x-> x.getProjectCompletion() == PENDING)
                .sorted(Comparator.comparing(Project::getTitle))
                .toList();

        if(projectList.isEmpty())
            return new ResponseEntity<>(ResponseUtils.NO_PENDING_PROJECT, HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Pending projects", projectList.stream()
                .skip(pageNum - 1).limit(pageSize).map(ProjectResponseDto::new).collect(Collectors.toList())));
    }

    @Override
    public ResponseEntity<?> findStartedProjects(int pageNum, int pageSize) {
        List<Project> projectList = projectRepository.findAll().stream()
                .filter(Project::getActiveStatus)
                .filter(x-> x.getProjectCompletion() == STARTED)
                .toList();

        if(projectList.isEmpty())
            return new ResponseEntity<>(ResponseUtils.NO_STARTED_PROJECT, HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Started projects", projectList.stream()
                .skip(pageNum - 1).limit(pageSize).map(ProjectResponseDto::new).collect(Collectors.toList())));
    }

    @Override
    public ResponseEntity<?> findCompletedProjects(int pageNum, int pageSize) {
        List<Project> projectList = projectRepository.findAll().stream()
                .filter(Project::getActiveStatus)
                .filter(x-> x.getProjectCompletion() == COMPLETED)
                .sorted(Comparator.comparing(Project::getTitle))
                .toList();

        if(projectList.isEmpty())
            return new ResponseEntity<>(ResponseUtils.N0_COMPLETED_PROJECT, HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Completed projects", projectList.stream()
                .skip(pageNum - 1).limit(pageSize).map(ProjectResponseDto::new).collect(Collectors.toList())));
    }

    @Override
    public ResponseEntity<?> updateProject(String projectId, ProjectUpdateDto projectUpdateDto) {
        Project project = projectRepository.findByProjectId(projectId)
                .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_PROJECT_BY_ID, projectId)));

        project.setCoverImage(projectUpdateDto.getCoverImage());
        project.setTitle(projectUpdateDto.getTitle());
        //project.setClientUsername(projectUpdateDto.getClientUsername());
        project.setActiveStatus(true);
        project.setModifiedOn(LocalDateTime.now());

        //Save to repository
        projectRepository.save(project);

        logger.info("Project has been successfully updated");
        //Getting the details of the user
        UserEntity user = userRepository.findByUsername(project.getClientUsername())
                .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_USER_BY_USERNAME, project.getClientUsername())));


        //Sending email to the owner of the project
        EmailDetail emailDetail = EmailDetail.builder()
                .recipient(user.getEmail())
                .subject(EmailResponse.PROJECT_UPDATE_SUBJECT)
                .body(String.format(EmailResponse.PROJECT_UPDATE_MAIL, project.getTitle(), project.getProjectId()))
                .build();
        emailService.sendSimpleEmail(emailDetail);

        return ResponseEntity.ok(ResponseUtils.PROJECT_UPDATE_SUCCESSFUL);
    }

    @Override
    public ResponseEntity<?> serviceProviderProjectUpdate(String projectId, ProjectServiceProviderUpdateDto serviceProviderUpdateDto) {
        Project project = projectRepository.findByProjectId(projectId)
                .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_PROJECT_BY_ID, projectId)));

        project.setServiceProviderUsername(serviceProviderUpdateDto.getServiceProviderUsername());
        project.setJobAcceptanceStatus(serviceProviderUpdateDto.getJobAcceptanceStatus());
        project.setProjectCompletion(serviceProviderUpdateDto.getProjectCompletion());
        project.setStartDate(serviceProviderUpdateDto.getStartDate());
        project.setEndDate(serviceProviderUpdateDto.getEndDate());
        project.setModifiedOn(LocalDateTime.now());

        projectRepository.save(project);

        // TODO: 05/09/2023 Email should be sent to the project owner that a service provider has accepted the project 

        //Getting the details of the user
        UserEntity user = userRepository.findByUsername(serviceProviderUpdateDto.getServiceProviderUsername())
                .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_USER_BY_USERNAME, serviceProviderUpdateDto.getServiceProviderUsername())));


        //Sending email to the project owner that a service provider the owner selected has accepted the offer.
        if(project.getJobAcceptanceStatus().equals(JobAcceptanceStatus.ACCEPTED)){
            EmailDetail emailDetail = EmailDetail.builder()
                    .recipient(user.getEmail())
                    .subject(EmailResponse.PROJECT_UPDATE_SUBJECT)
                    .body(String.format(EmailResponse.PROJECT_SERVICE_UPDATE_MAIL, project.getClientUsername(),
                            serviceProviderUpdateDto.getServiceProviderUsername(), project.getTitle() , project.getProjectId(), user.getChargePerHour()))
                    .build();

            emailService.sendSimpleEmail(emailDetail);
        }

        //Sending email to the project owner that the service provider has completed the project
        else if(project.getProjectCompletion().equals(COMPLETED)){
            EmailDetail emailDetail = EmailDetail.builder()
                    .recipient(user.getEmail())
                    .subject(EmailResponse.PROJECT_UPDATE_SUBJECT)
                    .body(String.format(EmailResponse.PROJECT_COMPLETION_MAIL, project.getClientUsername(), project.getTitle() , project.getProjectId()))
                    .build();

            emailService.sendSimpleEmail(emailDetail);
        }

        return ResponseEntity.ok(ResponseUtils.PROJECT_UPDATE_SUCCESSFUL);

    }

    @Override
    public ResponseEntity<?> adminProjectUpdate(String projectId, ProjectAdminUpdateDto adminUpdateDto) {
        Project project = projectRepository.findByProjectId(projectId)
                .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_PROJECT_BY_ID, projectId)));

        project.setProjectLevelApproval(adminUpdateDto.getProjectLevelApproval());

        projectRepository.save(project);

        return ResponseEntity.ok(ResponseUtils.PROJECT_UPDATE_SUCCESSFUL);
    }

    @Override
    public ResponseEntity<?> deleteProject(String projectId) {
        Project project = projectRepository.findByProjectId(projectId)
                .filter(Project::getActiveStatus)
                .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_PROJECT_BY_ID,
                        projectId)));

        //Change the active status of the 'deleted' book
        project.setActiveStatus(false);
        //Save to repository
        projectRepository.save(project);
        return ResponseEntity.ok(ResponseUtils.PROJECT_DELETED);
    }

//    @Override
//    public ResponseEntity<?> searchProject(String username, String title, String projectId, int pageNum, int pageSize) {
//        List<Project> bookEntity = new ArrayList<>();
//
//        if(StringUtils.hasText(username))
//            bookEntity = projectRepository.findByUsername(username)
//                    .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_PROJECT_BY_NAME, username)));
//
//        if(StringUtils.hasText(title))
//            bookEntity = projectRepository.findByTitle(title)
//                    .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_PROJECT_BY_NAME, title)));
//
//        if(StringUtils.hasText(projectId))
//            bookEntity.add(projectRepository.findByProjectId(projectId).filter(Project::getActiveStatus)
//                    .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_PROJECT_BY_ID, projectId))));
//
//        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Completed projects", bookEntity.stream()
//                .filter(Project::getActiveStatus)
//                .skip(pageNum).limit(pageSize).map(ProjectResponseDto::new)));
//
//    }

}
