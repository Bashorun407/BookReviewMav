package com.akinnova.BookReviewMav.dto.userdto;

import com.akinnova.BookReviewMav.enums.*;
import lombok.Data;

@Data
public class UserUpdateDto {
    private String username;
    private String profilePicture;
    private String dateOfBirth;
    private String phoneNumber;
    private String email;
    private String password;
    private UserRole userRole;
    private UserType userType;
    private Specialization specialization;
    private ApplicationStatus applicationStatus;
    private String description;
    private Boolean activeStatus;
}
