package com.akinnova.BookReviewMav.dto.projectdto;

import com.akinnova.BookReviewMav.enums.ProjectLevelApproval;
import lombok.Data;

@Data
public class ProjectAdminUpdateDto {
    private String projectId;
    private ProjectLevelApproval projectLevelApproval;
}
