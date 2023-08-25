package com.akinnova.BookReviewMav.dto.userdto;

import com.akinnova.BookReviewMav.enums.UserRoleEnum;
import lombok.Data;

@Data
public class UserCreateDto {
    private String profilePicture;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String phoneNumber;
    private String username;
    private String email;
    private String password;
    private UserRoleEnum userRoleEnum;
    //private String roleName;
    private String specialization;
    private String description;
}
