package com.akinnova.BookReviewMav.dto.commentdto;

import com.akinnova.BookReviewMav.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {
    private String comment;
    private LocalDateTime commentTime;

    public CommentResponseDto(Comment comment){
        this.comment = comment.getComment();
        this.commentTime = comment.getCommentTime();
    }
}
