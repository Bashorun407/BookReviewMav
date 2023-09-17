package com.akinnova.BookReviewMav.dto.userdto;

import com.akinnova.BookReviewMav.entity.UserEntity;
import lombok.Data;

@Data
public class UserResponseDto {
    private String profilePicture;
    private String firstName;
    private String lastName;
    private String username;
    private String description;
    private String userId;

    public UserResponseDto(UserEntity userEntity){
        this.profilePicture = userEntity.getProfilePicture();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.username = userEntity.getUsername();
        this.description = userEntity.getDescription();
        this.userId = userEntity.getUserId();
    }
}

