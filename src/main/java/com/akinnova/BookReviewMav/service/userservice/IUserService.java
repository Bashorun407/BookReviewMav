package com.akinnova.BookReviewMav.service.userservice;

import com.akinnova.BookReviewMav.dto.userdto.UserCreateDto;
import com.akinnova.BookReviewMav.dto.userdto.UserResponseDto;
import com.akinnova.BookReviewMav.dto.userdto.UserUpdateDto;
import com.akinnova.BookReviewMav.response.ResponsePojo;
import org.springframework.http.ResponseEntity;

public interface IUserService {

    ResponsePojo<UserResponseDto> addUser(UserCreateDto userCreateDto);
    ResponseEntity<?> allUsers(int pageNum, int pageSize);
    ResponseEntity<?> SearchUser(String username, String phoneNumber, String email);
    ResponseEntity<?> FindClients(int pageNum, int pageSize);
    ResponseEntity<?> FindServiceProviders(int pageNum, int pageSize);
    ResponseEntity<?> FindAdmins(int pageNum, int pageSize);
    ResponseEntity<?> updateUser(UserUpdateDto userUpdateDto);
    ResponseEntity<?> deleteUser(String username);

}
