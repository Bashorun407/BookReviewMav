package com.akinnova.BookReviewMav.dto.projectdto;

import com.akinnova.BookReviewMav.enums.JobAcceptanceStatus;
import com.akinnova.BookReviewMav.enums.ProjectCompletion;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProjectServiceProviderUpdateDto {
    private String serviceProviderUsername;
    @Enumerated(EnumType.STRING)
    private JobAcceptanceStatus jobAcceptanceStatus;
    @Enumerated(EnumType.STRING)
    private ProjectCompletion projectCompletion;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
