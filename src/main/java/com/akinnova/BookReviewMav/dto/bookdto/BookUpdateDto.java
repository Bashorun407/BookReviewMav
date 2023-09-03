package com.akinnova.BookReviewMav.dto.bookdto;

import lombok.Data;

@Data
public class BookUpdateDto {
    private String coverImage;
    private String title;
    private String content;
    private String username;
    private String projectId;
    private Boolean activeStatus;
}
