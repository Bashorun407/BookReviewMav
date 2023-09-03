package com.akinnova.BookReviewMav.service.userservice;

import com.akinnova.BookReviewMav.dto.userdto.AdminUpdateDto;
import com.akinnova.BookReviewMav.dto.userdto.UserCreateDto;
import com.akinnova.BookReviewMav.dto.userdto.UserResponseDto;
import com.akinnova.BookReviewMav.dto.userdto.UserUpdateDto;
import com.akinnova.BookReviewMav.entity.UserEntity;
import com.akinnova.BookReviewMav.enums.*;
import com.akinnova.BookReviewMav.exception.ApiException;
import com.akinnova.BookReviewMav.repository.UserRepository;
import com.akinnova.BookReviewMav.response.ResponsePojo;
import com.akinnova.BookReviewMav.response.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static com.akinnova.BookReviewMav.enums.ApplicationStatus.NOT_RECEIVED;
import static com.akinnova.BookReviewMav.enums.ReviewStatus.NOT_CONFIRMED;
import static com.akinnova.BookReviewMav.enums.UserRole.*;
import static com.akinnova.BookReviewMav.enums.UserType.CLIENT;
import static com.akinnova.BookReviewMav.enums.UserType.SERVICE_PROVIDER;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponsePojo<UserResponseDto> addUser(UserCreateDto userCreateDto) {
        boolean check = userRepository.existsByUsername(userCreateDto.getUsername());

        if(check)
            throw new ApiException(String.format("User with username: %s already exists", userCreateDto.getUsername()));

        //Add and save new client to repository
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .firstName(userCreateDto.getFirstName())
                .lastName(userCreateDto.getLastName())
                .userId(ResponseUtils.generateUniqueIdentifier(10, userCreateDto.getUsername()))
                .username(userCreateDto.getUsername())
                .email(userCreateDto.getEmail())
                .password(userCreateDto.getPassword())
                .userRole(REGULAR_USER)
                .userType(CLIENT)
                .specialization(Specialization.NONE)
                .applicationStatus(NOT_RECEIVED)
                .reviewStatus(NOT_CONFIRMED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        return new ResponsePojo<>(ResponseType.SUCCESS, "New user added successfully", new UserResponseDto(userEntity));
    }

    @Override
    public ResponseEntity<?> allUsers(int pageNum, int pageSize) {
        List<UserEntity> userEntityList = userRepository.findAll().stream().filter(UserEntity::getActiveStatus).toList();

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All users", userEntityList.stream()
                .sorted(Comparator.comparing(UserEntity::getLastName).thenComparing(UserEntity::getFirstName))
                .skip(pageNum - 1).limit(pageSize).map(UserResponseDto::new)));
    }

    @Override
    public ResponseEntity<?> SearchUser(String username, String phoneNumber, String email) {

        UserEntity userEntity = new UserEntity();

        if(StringUtils.hasText(username))
            userEntity = userRepository.findByUsername(username).filter(UserEntity::getActiveStatus)
                    .orElseThrow(()-> new ApiException(String.format("There is no user by username: %s ", username)));

        if(StringUtils.hasText(email))
            userEntity = userRepository.findByEmail(username).filter(UserEntity::getActiveStatus)
                    .orElseThrow(()-> new ApiException(String.format("There is no user by username: %s ", email)));

        return new ResponseEntity<>(new UserResponseDto(userEntity), HttpStatus.FOUND);
    }

    // TODO: 13/08/2023 To implement the following methods
    @Override
    public ResponseEntity<?> FindClients(int pageNum, int pageSize) {
        List<UserEntity> userEntityList = userRepository.findAll().stream()
                .filter(x-> x.getUserType() == CLIENT)
                .filter(UserEntity::getActiveStatus)
                .sorted(Comparator.comparing(UserEntity::getLastName).thenComparing(UserEntity::getFirstName))
                .toList();

        if(userEntityList.isEmpty())
            return new ResponseEntity<>("There are no Clients yet.", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All clients", userEntityList.stream()
                .skip(pageNum - 1).limit(pageSize).map(UserResponseDto::new)));
    }

    @Override
    public ResponseEntity<?> FindServiceProviders(int pageNum, int pageSize) {
        List<UserEntity> userEntityList = userRepository.findAll().stream()
                .filter(x-> x.getUserType() == SERVICE_PROVIDER)
                .filter(UserEntity::getActiveStatus)
                .sorted(Comparator.comparing(UserEntity::getLastName).thenComparing(UserEntity::getFirstName))
                .toList();

        if(userEntityList.isEmpty())
            return new ResponseEntity<>("There are no Service Providers yet.", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All service providers", userEntityList.stream()
                .skip(pageNum - 1).limit(pageSize).map(UserResponseDto::new)));

    }

    @Override
    public ResponseEntity<?> FindRegularUsers(int pageNum, int pageSize) {
        List<UserEntity> userEntityList = userRepository.findAll().stream()
                .filter(x-> x.getUserRole() == REGULAR_USER)
                .filter(UserEntity::getActiveStatus)
                .sorted(Comparator.comparing(UserEntity::getLastName).thenComparing(UserEntity::getFirstName))
                .toList();

        if(userEntityList.isEmpty())
            return new ResponseEntity<>("There are no regular users yet.", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All regular users", userEntityList.stream()
                .skip(pageNum - 1).limit(pageSize).map(UserResponseDto::new)));
    }

    @Override
    public ResponseEntity<?> FindAdmins(int pageNum, int pageSize) {
        List<UserEntity> userEntityList = userRepository.findAll().stream()
                .filter(x-> x.getUserRole() == ADMIN)
                .filter(UserEntity::getActiveStatus)
                .sorted(Comparator.comparing(UserEntity::getLastName).thenComparing(UserEntity::getFirstName))
                .toList();

        if(userEntityList.isEmpty())
            return new ResponseEntity<>("There are no Admins yet.", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All admins", userEntityList.stream()
                .skip(pageNum - 1).limit(pageSize).map(UserResponseDto::new)));

    }


    @Override
    public ResponseEntity<?> updateUser(UserUpdateDto userUpdateDto) {
        UserEntity userEntity = userRepository.findByUsername(userUpdateDto.getUsername())
                .orElseThrow(()-> new ApiException(String.format(" There is no user by username: %s ", userUpdateDto.getUsername())));

        userEntity.setProfilePicture(userUpdateDto.getProfilePicture());
        userEntity.setDateOfBirth(userUpdateDto.getDateOfBirth());
        userEntity.setPhoneNumber(userUpdateDto.getPhoneNumber());
        userEntity.setEmail(userUpdateDto.getEmail());
        userEntity.setPassword(userUpdateDto.getPassword());
        userEntity.setSpecialization(userUpdateDto.getSpecialization());
        userEntity.setApplicationStatus(userUpdateDto.getApplicationStatus());
        userEntity.setDescription(userUpdateDto.getDescription());
        userEntity.setActiveStatus(true);
        userEntity.setModifiedOn(LocalDateTime.now());

        //Save to repository
        userRepository.save(userEntity);

        return ResponseEntity.ok("user details has been updated successfully.");
    }

    @Override
    public ResponseEntity<?> jobRoleUpdate(AdminUpdateDto adminUpdateDto) {
        UserEntity userEntity = userRepository.findByUsername(adminUpdateDto.getUsername())
                .orElseThrow(()-> new ApiException(String.format(" There is no user by username: %s ", adminUpdateDto.getUsername())));

        userEntity.setUserRole(adminUpdateDto.getUserRole());
        userEntity.setUserType(adminUpdateDto.getUserType());
        userEntity.setReviewStatus(adminUpdateDto.getReviewStatus());

        //Save to repository
        userRepository.save(userEntity);
        return ResponseEntity.ok("user details has been updated successfully.");

    }

    @Override
    public ResponseEntity<?> deleteUser(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).filter(UserEntity::getActiveStatus)
                .orElseThrow(()-> new ApiException(String.format("There is no user by username: %s ", username)));

        //Only resets active status to 'false'
        userEntity.setActiveStatus(false);
        //Save to database
        userRepository.save(userEntity);

        return ResponseEntity.ok("User has been deleted.");
    }
}
