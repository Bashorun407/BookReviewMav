package com.akinnova.BookReviewMav.service.commentservice;

import com.akinnova.BookReviewMav.dto.commentdto.CommentDeleteDto;
import com.akinnova.BookReviewMav.dto.commentdto.CommentDto;
import com.akinnova.BookReviewMav.dto.commentdto.CommentResponseDto;
import com.akinnova.BookReviewMav.entity.Comment;
import com.akinnova.BookReviewMav.enums.ResponseType;
import com.akinnova.BookReviewMav.exception.ApiException;
import com.akinnova.BookReviewMav.repository.CommentRepository;
import com.akinnova.BookReviewMav.response.ResponsePojo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService{

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public ResponseEntity<?> addComment(CommentDto commentDto) {
        //Comment and save simultaneously
        commentRepository.save(Comment.builder()
                .comment(commentDto.getComment())
                .username(commentDto.getUsername())
                .commentTime(LocalDateTime.now())
                .build());
        return ResponseEntity.ok("Done");
    }

    @Override
    public ResponseEntity<?> allComments(int pageNum, int pageSize) {

        List<Comment> commentList = commentRepository.findAll();

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All comments", commentList.stream()
                .skip(pageNum - 1).limit(pageSize).map(CommentResponseDto::new)));
    }

    @Override
    public ResponseEntity<?> findCommentByTitle(String title, int pageNum, int pageSize) {
        List<Comment> commentList = commentRepository.findByTitle(title)
                .orElseThrow(()-> new ApiException(String.format("There are no comments by title: %s yet", title)));

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Comments By title.",
                commentList.stream().skip(pageNum - 1).limit(pageSize).map(CommentResponseDto::new)));
    }

    @Override
    public ResponseEntity<?> findCommentByUsername(String username, int pageNum, int pageSize) {
        List<Comment> commentList = commentRepository.findByUsername(username)
                .orElseThrow(()-> new ApiException(String.format("There are no comments by username: %s yet", username)));

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Comments By title.",
                commentList.stream().skip(pageNum - 1).limit(pageSize).map(CommentResponseDto::new)));
    }

    @Override
    public ResponseEntity<?> deleteComment(CommentDeleteDto commentDeleteDto) {

        //Find comments by username
        List<Comment> commentList = commentRepository.findByUsername(commentDeleteDto.getUsername())
                .orElseThrow(()-> new ApiException(String.format("Comments by username: %s not available", commentDeleteDto.getUsername())));

        //to remove a comment by user at a specific time
        commentList.stream().filter(x -> x.getTitle().equals(commentDeleteDto.getProjectId()))
                .peek(comment -> {
                    if (comment.getCommentTime().isEqual(commentDeleteDto.getDateTime()))
                        commentRepository.delete(comment);
                });

        return ResponseEntity.ok("Comment deleted successfully");
    }
}
