package com.akinnova.BookReviewMav.servicetest;

import com.akinnova.BookReviewMav.dto.commentdto.CommentDto;
import com.akinnova.BookReviewMav.dto.commentdto.CommentResponseDto;
import com.akinnova.BookReviewMav.entity.Comment;
import com.akinnova.BookReviewMav.enums.ResponseType;
import com.akinnova.BookReviewMav.repository.CommentRepository;
import com.akinnova.BookReviewMav.response.ResponsePojo;
import com.akinnova.BookReviewMav.service.commentservice.CommentServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {CommentServiceMockitoTest.class})
public class CommentServiceMockitoTest {

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentServiceImpl commentService;

    @Test
    @Order(1)
    public void test_addComment(){
        CommentDto commentDto = CommentDto.builder().comment("How far?").username("OluDot").build();

        Comment comment = Comment.builder().id(1L).comment("How far?").commentTime(LocalDateTime.now())
                .username("OluDot").build();

        when(commentRepository.save(comment)).thenReturn(comment);

        assertEquals(ResponseEntity.ok("Done"), commentService.addComment(commentDto));

    }
    @Test
    @Order(2)
    public void test_allComments(){
       List<Comment> commentList = new ArrayList<>();

        commentList.add(Comment.builder().id(1L).comment("Hello friend").username("Bolzmann")
                .commentTime(LocalDateTime.now()).build());
        commentList.add(Comment.builder().id(2L).comment("Hi fella").username("OluDot")
                .commentTime(LocalDateTime.now()).build());

        //Mock return data
        List<CommentResponseDto> commentResponseDtos = commentList.stream().map(CommentResponseDto::new).collect(Collectors.toList());
        ResponseEntity<ResponsePojo<List<CommentResponseDto>>> expectedResult = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All comments", commentResponseDtos));


        when(commentRepository.findAll()).thenReturn(commentList); //Mock

        assertEquals(expectedResult, commentService.allComments(1, 2));

    }


    @Test
    @Order(3)
    public void test_commentByUsername(){
        List<Comment> commentList = new ArrayList<>();

        commentList.add(Comment.builder().id(1L).comment("Hello friend").username("Bolzmann")
                .commentTime(LocalDateTime.now()).build());
        commentList.add(Comment.builder().id(2L).comment("Hi fella").username("OluDot")
                .commentTime(LocalDateTime.now()).build());

        //filtered data by username
        List<Comment> usernameCommentList = commentList.stream().filter(x -> x.getUsername().equals("OluDot")).collect(Collectors.toList());

        //Conversion of results into the format of the return type of the actual method
        List<CommentResponseDto> commentResponseDtos = commentList.stream().filter(x-> x.getUsername().equals("OluDot")).map(CommentResponseDto::new).collect(Collectors.toList());
        ResponseEntity<ResponsePojo<List<CommentResponseDto>>> expectedResult = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Comments by username", commentResponseDtos));

        when(commentRepository.findByUsername("OluDot")).thenReturn(Optional.of(usernameCommentList));

        assertEquals(expectedResult, commentService.findCommentByUsername("OluDot", 1, 2));

        verify(commentRepository).findByUsername("OluDot");
    }

//    @Test
//    @Order(4)
//    public void test_deleteComment(){
//        List<Comment> commentList = new ArrayList<>();
//
//        commentList.add(Comment.builder().id(1L).comment("Hello friend").username("Bolzmann")
//                .commentTime(LocalDateTime.now()).build());
//
//        Comment comment = Comment.builder().id(2L).comment("Hi fella").username("OluDot")
//                .commentTime(LocalDateTime.now()).build();
//        commentList.add(comment);
//
//        //filtered data by username
//        List<Comment> usernameCommentList = commentList.stream().filter(x -> x.getUsername().equals("OluDot")).collect(Collectors.toList());
//
//        CommentDeleteDto commentDeleteDto = CommentDeleteDto.builder().username("OluDot").dateTime(LocalDateTime.now()).build();
//
//        //To verify that the delete method is invoked at least once.
//        commentService.deleteComment(commentDeleteDto);
//        verify(commentRepository, times(1).delete(comme));
//
//    }
}
