package com.akinnova.BookReviewMav.dto.bookdto;

import com.akinnova.BookReviewMav.entity.BookEntity;
import lombok.Data;

@Data
public class BookResponseDto {
    private String coverImage;
    private String title;
    private String username;
    private String projectId;


    public BookResponseDto(BookEntity bookEntity){
        this.coverImage = bookEntity.getCoverImage();
        this.title = bookEntity.getTitle();
        this.username = bookEntity.getUsername();
        this.projectId = bookEntity.getProjectId();

    }
}
