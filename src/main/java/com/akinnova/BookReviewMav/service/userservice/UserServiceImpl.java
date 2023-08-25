package com.akinnova.BookReviewMav.service.userservice;

import com.akinnova.BookReviewMav.dto.userdto.UserCreateDto;
import com.akinnova.BookReviewMav.dto.userdto.UserResponseDto;
import com.akinnova.BookReviewMav.dto.userdto.UserUpdateDto;
import com.akinnova.BookReviewMav.entity.UserEntity;
import com.akinnova.BookReviewMav.enums.ResponseType;
import com.akinnova.BookReviewMav.exception.ApiException;
import com.akinnova.BookReviewMav.repository.UserRepository;
import com.akinnova.BookReviewMav.response.ResponsePojo;
import com.akinnova.BookReviewMav.response.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.akinnova.BookReviewMav.enums.UserRoleEnum.*;

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
                .profilePicture(userCreateDto.getProfilePicture())
                .firstName(userCreateDto.getFirstName())
                .lastName(userCreateDto.getLastName())
                .userId(ResponseUtils.generateUniqueIdentifier(10, userCreateDto.getUsername()))
                .dateOfBirth(userCreateDto.getDateOfBirth())
                .phoneNumber(userCreateDto.getPhoneNumber())
                .username(userCreateDto.getUsername())
                .email(userCreateDto.getEmail())
                .password(userCreateDto.getPassword())
                .userRoleEnum(userCreateDto.getUserRoleEnum())
                .activeStatus(true)
                //.userRole(userCreateDto.getRoleName())
                .description(userCreateDto.getDescription())
                .build());

        return new ResponsePojo<>(ResponseType.SUCCESS, "New user added successfully", new UserResponseDto(userEntity));
    }

    @Override
    public ResponseEntity<?> allUsers(int pageNum, int pageSize) {
        List<UserEntity> userEntityList = userRepository.findAll().stream().filter(UserEntity::getActiveStatus).toList();

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All users", userEntityList.stream()
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
                .filter(x-> x.getUserRoleEnum() == Client)
                .filter(UserEntity::getActiveStatus)
                .toList();

        if(userEntityList.isEmpty())
            return new ResponseEntity<>("There are no Clients yet.", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All clients", userEntityList.stream()
                .skip(pageNum - 1).limit(pageSize).map(UserResponseDto::new)));
    }

    @Override
    public ResponseEntity<?> FindServiceProviders(int pageNum, int pageSize) {
        List<UserEntity> userEntityList = userRepository.findAll().stream()
                .filter(x-> x.getUserRoleEnum() == Service_Provider)
                .filter(UserEntity::getActiveStatus)
                .toList();

        if(userEntityList.isEmpty())
            return new ResponseEntity<>("There are no Service Providers yet.", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All service providers", userEntityList.stream()
                .skip(pageNum - 1).limit(pageSize).map(UserResponseDto::new)));

    }

    @Override
    public ResponseEntity<?> FindAdmins(int pageNum, int pageSize) {
        List<UserEntity> userEntityList = userRepository.findAll().stream()
                .filter(x-> x.getUserRoleEnum() == ADMIN)
                .filter(UserEntity::getActiveStatus)
                .toList();

        if(userEntityList.isEmpty())
            return new ResponseEntity<>("There are no Admins yet.", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All admins", userEntityList.stream()
                .skip(pageNum - 1).limit(pageSize).map(UserResponseDto::new)));

    }

    @Override
    public ResponseEntity<?> updateUser(UserUpdateDto userUpdateDto) {
        UserEntity userEntity = userRepository.findByUsername(userUpdateDto.getUsername()).filter(UserEntity::getActiveStatus)
                .orElseThrow(()-> new ApiException(String.format(" There is no user by username: %s ", userUpdateDto.getUsername())));

        userEntity.setProfilePicture(userUpdateDto.getProfilePicture());
        userEntity.setPhoneNumber(userUpdateDto.getPhoneNumber());
        userEntity.setUsername(userUpdateDto.getUsername());
        userEntity.setEmail(userUpdateDto.getEmail());
        userEntity.setPassword(userUpdateDto.getPassword());
        userEntity.setUserRoleEnum(userUpdateDto.getUserRoleEnum());
        userEntity.setDescription(userUpdateDto.getDescription());
        userEntity.setModifiedOn(LocalDateTime.now());

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
