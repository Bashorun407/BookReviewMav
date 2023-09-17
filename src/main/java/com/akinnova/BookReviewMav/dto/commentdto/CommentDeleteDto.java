package com.akinnova.BookReviewMav.dto.commentdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommentDeleteDto {
    private String username;
    private LocalDateTime dateTime;
}
