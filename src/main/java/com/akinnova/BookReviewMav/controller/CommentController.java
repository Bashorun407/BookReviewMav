package com.akinnova.BookReviewMav.controller;

import com.akinnova.BookReviewMav.dto.commentdto.CommentDeleteDto;
import com.akinnova.BookReviewMav.dto.commentdto.CommentDto;
import com.akinnova.BookReviewMav.service.commentservice.CommentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment/auth")

public class CommentController {

    private final CommentServiceImpl commentService;
    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/addComment")
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto) {
        return commentService.addComment(commentDto);
    }

    @GetMapping("/allComments")
    public ResponseEntity<?> allComments(@RequestParam(defaultValue = "1") int pageNum,
                                         @RequestParam(defaultValue = "20") int pageSize) {
        return commentService.allComments(pageNum, pageSize);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> findCommentByUsername(@PathVariable String username,
                                                   @RequestParam(defaultValue = "1") int pageNum,
                                                   @RequestParam(defaultValue = "10") int pageSize) {
        return commentService.findCommentByUsername(username, pageNum, pageSize);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestBody CommentDeleteDto commentDeleteDto) {
        return commentService.deleteComment(commentDeleteDto);
    }
}
