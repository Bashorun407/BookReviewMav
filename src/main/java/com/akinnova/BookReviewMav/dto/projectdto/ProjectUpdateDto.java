package com.akinnova.BookReviewMav.dto.projectdto;

import lombok.Data;

@Data
public class ProjectUpdateDto {
    private String coverImage;
    private String title;
    private String content;
    private String username;
    private String projectId;
    private Boolean activeStatus;
}
