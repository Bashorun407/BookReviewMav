package com.akinnova.BookReviewMav.dto.userdto;

import com.akinnova.BookReviewMav.enums.ApplicationStatus;
import com.akinnova.BookReviewMav.enums.Specialization;
import com.akinnova.BookReviewMav.enums.UserType;
import lombok.Data;

@Data
public class ServiceProviderUpdateDto {
    private String username;
    private UserType userType;
    private Specialization specialization;
    private String description;
    private Double chargePerHour;
    private ApplicationStatus applicationStatus;
    private Boolean activeStatus;
}
