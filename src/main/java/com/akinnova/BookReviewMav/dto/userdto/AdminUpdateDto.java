package com.akinnova.BookReviewMav.dto.userdto;

import com.akinnova.BookReviewMav.enums.ApplicationStatus;

import com.akinnova.BookReviewMav.enums.ReviewStatus;
import com.akinnova.BookReviewMav.enums.UserRole;
import com.akinnova.BookReviewMav.enums.UserType;
import lombok.Data;

@Data
public class AdminUpdateDto {
    private String username;
    private UserRole userRole;
    private UserType userType;
    private ReviewStatus reviewStatus;
}
