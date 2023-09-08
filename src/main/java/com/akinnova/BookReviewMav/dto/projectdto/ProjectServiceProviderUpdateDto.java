package com.akinnova.BookReviewMav.dto.projectdto;

import com.akinnova.BookReviewMav.enums.ProjectCompletion;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectServiceProviderUpdateDto {
    private String projectId;
    private String serviceProvider;
    private ProjectCompletion projectCompletion;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
