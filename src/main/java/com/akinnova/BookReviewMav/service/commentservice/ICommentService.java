package com.akinnova.BookReviewMav.service.commentservice;

import com.akinnova.BookReviewMav.dto.commentdto.CommentDeleteDto;
import com.akinnova.BookReviewMav.dto.commentdto.CommentDto;
import org.springframework.http.ResponseEntity;

public interface ICommentService {
    ResponseEntity<?> addComment(CommentDto commentDto);
    ResponseEntity<?> allComments(int pageNum, int pageSize);
    //ResponseEntity<?> findCommentByTitle(String title, int pageNum, int pageSize);
    ResponseEntity<?> findCommentByUsername(String username, int pageNum, int pageSize);
    ResponseEntity<?> deleteComment(CommentDeleteDto commentDeleteDto);

}
