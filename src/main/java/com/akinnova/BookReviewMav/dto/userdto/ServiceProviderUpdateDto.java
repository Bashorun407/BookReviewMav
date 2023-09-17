package com.akinnova.BookReviewMav.dto.userdto;

import com.akinnova.BookReviewMav.entity.UserEntity;
import com.akinnova.BookReviewMav.enums.ApplicationStatus;
import com.akinnova.BookReviewMav.enums.ServiceProviderSpecialization;
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
public class ServiceProviderUpdateDto {
    //private String username;
    private UserType userType;
    @Enumerated(EnumType.STRING)
    private ServiceProviderSpecialization specialization;
    private String description;
    private Double chargePerHour;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;
    private Boolean activeStatus;

    public ServiceProviderUpdateDto(UserEntity user){
        //this.username = user.getUsername();
        this.userType = user.getUserType();
        this.specialization = user.getSpecialization();
        this.description = user.getDescription();
        this.chargePerHour = user.getChargePerHour();
        this.applicationStatus = user.getApplicationStatus();
        this.activeStatus = true;
    }
}
