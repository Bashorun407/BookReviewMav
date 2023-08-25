package com.akinnova.BookReviewMav.dto.userdto;

import com.akinnova.BookReviewMav.entity.UserEntity;
import lombok.Data;

@Data
//@Builder
public class UserResponseDto {
    private String profilePicture;
    private String firstName;
    private String lastName;
    private String description;

    public UserResponseDto(UserEntity userEntity){
        this.profilePicture = userEntity.getProfilePicture();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.description = userEntity.getDescription();
    }
}
