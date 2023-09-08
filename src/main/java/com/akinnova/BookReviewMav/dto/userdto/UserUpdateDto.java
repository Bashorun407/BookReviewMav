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

}
