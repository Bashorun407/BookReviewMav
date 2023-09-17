package com.akinnova.BookReviewMav.controllertest;

import com.akinnova.BookReviewMav.controller.CommentController;
import com.akinnova.BookReviewMav.entity.Comment;
import com.akinnova.BookReviewMav.service.commentservice.CommentServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {CommentControllerMockitoTest.class})
public class CommentControllerMockitoTest {

    @Mock
    CommentServiceImpl commentService;

    @InjectMocks
    CommentController commentController;

    List<Comment> commentList;

    Comment comment;

    @Test
    @Order(1)
    public void test_allComments(){
        commentList = new ArrayList<>();

        commentList.add(Comment.builder()
                .id(1L)
                .username("BashOlu")
                .comment("Hello")
                .commentTime(LocalDateTime.now())
                .build());

        commentList.add(Comment.builder()
                .id(2L)
                .username("OluDot")
                .comment("Hi")
                .commentTime(LocalDateTime.now())
                .build());

        //when(commentService.allComments(1, 2)).then;
    }

    @Test
    @Order(2)
    public void test_findCommentsByUsername(){
        comment = Comment.builder()
                .id(1L)
                .username("BashOlu")
                .comment("Hello")
                .commentTime(LocalDateTime.now())
                .build();

        String username = comment.getUsername();
       // when(commentService.findCommentByUsername(username, 1, 2)).thenReturn(comment);
    }
}
