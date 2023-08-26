package com.akinnova.BookReviewMav.dto.userdto;

import com.akinnova.BookReviewMav.enums.UserRoleEnum;
import lombok.Data;

@Data
public class UserUpdateDto {
    private String profilePicture;
    private String phoneNumber;
    private String username;
    private String email;
    private String password;
    private UserRoleEnum userRoleEnum;
    private String specialization;
    private String description;
}
