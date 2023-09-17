package com.akinnova.BookReviewMav.dto.userdto;


import com.akinnova.BookReviewMav.enums.ApplicationReviewStatus;
import com.akinnova.BookReviewMav.enums.UserRole;
import com.akinnova.BookReviewMav.enums.UserType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AdminUpdateDto {
    //private String username;
    private UserRole userRole;
    private UserType userType;
    @Enumerated(EnumType.STRING)
    private ApplicationReviewStatus reviewStatus;
}
