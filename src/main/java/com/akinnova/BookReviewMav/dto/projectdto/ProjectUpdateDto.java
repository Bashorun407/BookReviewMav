package com.akinnova.BookReviewMav.dto.projectdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProjectUpdateDto {
    private String coverImage;
    private String title;
    private String content;
    //private String clientUsername;
    //private String projectId;
    private Boolean activeStatus;

}
