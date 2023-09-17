package com.akinnova.BookReviewMav.dto.projectdto;

import com.akinnova.BookReviewMav.enums.ProjectLevelApproval;
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
public class ProjectAdminUpdateDto {
    //private String projectId;
    @Enumerated(EnumType.STRING)
    private ProjectLevelApproval projectLevelApproval;
}
