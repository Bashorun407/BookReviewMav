package com.akinnova.BookReviewMav.dto.bookdto;

import com.akinnova.BookReviewMav.enums.ReviewStatus;
import lombok.Data;

@Data
public class BookUpdateDto {
    private String coverImage;
    private String title;
    private String content;
    private String author;
    private String projectId;
    private ReviewStatus reviewStatus;
    private Boolean activeStatus;}
