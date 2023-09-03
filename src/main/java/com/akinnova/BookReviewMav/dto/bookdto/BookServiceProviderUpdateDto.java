package com.akinnova.BookReviewMav.dto.bookdto;

import com.akinnova.BookReviewMav.enums.ProjectCompletion;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookServiceProviderUpdateDto {
    private String projectId;
    private String serviceProvider;
    private ProjectCompletion projectCompletion;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
