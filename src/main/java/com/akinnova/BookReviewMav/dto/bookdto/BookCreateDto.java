package com.akinnova.BookReviewMav.dto.bookdto;

import lombok.Data;

@Data
public class BookCreateDto {
    private String coverImage;
    private String title;
    private String content;
    private String author;
}
