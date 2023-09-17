package com.akinnova.BookReviewMav.dto.projectdto;

import com.akinnova.BookReviewMav.entity.Project;
import lombok.Data;

@Data
public class ProjectResponseDto {
    private String coverImage;
    private String title;
    private String clientUsername;
    private String projectId;


    public ProjectResponseDto(Project bookEntity){
        this.coverImage = bookEntity.getCoverImage();
        this.title = bookEntity.getTitle();
        this.clientUsername = bookEntity.getClientUsername();
        this.projectId = bookEntity.getProjectId();

    }
}
