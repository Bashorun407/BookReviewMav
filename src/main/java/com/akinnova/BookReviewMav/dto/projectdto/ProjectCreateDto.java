package com.akinnova.BookReviewMav.dto.projectdto;

import com.akinnova.BookReviewMav.enums.Category;
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
public class ProjectCreateDto {
    private String coverImage;
    private String title;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String content;
    private String clientUsername;
}
