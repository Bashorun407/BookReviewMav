package com.akinnova.BookReviewMav.dto.bookdto;

import com.akinnova.BookReviewMav.enums.Category;
import lombok.Data;

@Data
public class BookCreateDto {
    private String coverImage;
    private String title;
    private Category category;
    private String content;
    private String username;
}
