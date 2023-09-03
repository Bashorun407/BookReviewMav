package com.akinnova.BookReviewMav.dto.userdto;

import lombok.Data;

@Data
public class UserCreateDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}
