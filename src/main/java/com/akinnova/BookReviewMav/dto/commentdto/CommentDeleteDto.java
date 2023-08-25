package com.akinnova.BookReviewMav.dto.commentdto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDeleteDto {
    private String projectId;
    private String username;
    private LocalDateTime dateTime;
}
