package com.akinnova.BookReviewMav.dto.projectdto;

import com.akinnova.BookReviewMav.enums.Category;
import lombok.Data;

@Data
public class ProjectCreateDto {
    private String coverImage;
    private String title;
    private Category category;
    private String content;
    private String username;
}
