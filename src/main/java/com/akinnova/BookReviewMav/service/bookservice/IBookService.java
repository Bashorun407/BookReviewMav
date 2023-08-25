package com.akinnova.BookReviewMav.service.bookservice;

import com.akinnova.BookReviewMav.dto.bookdto.BookCreateDto;
import com.akinnova.BookReviewMav.dto.bookdto.BookResponseDto;
import com.akinnova.BookReviewMav.dto.bookdto.BookUpdateDto;
import com.akinnova.BookReviewMav.response.ResponsePojo;
import org.springframework.http.ResponseEntity;

public interface IBookService {
    ResponsePojo<BookResponseDto> addBook(BookCreateDto bookCreateDto);
    ResponseEntity<?> findAllBooks(int pageNum, int pageSize);
    ResponseEntity<?> findBookByAuthor(String author, int pageNum, int pageSize);
    ResponseEntity<?> findBookByTitle(String title, int pageNum, int pageSize);
    ResponseEntity<?> findBookByProjectId(String projectId);
    ResponseEntity<?> findPendingBookReview(int pageNum, int pageSize);
    ResponseEntity<?> findStartedBookReview(int pageNum, int pageSize);
    ResponseEntity<?> findCompletedBookReview(int pageNum, int pageSize);
    ResponseEntity<?> updateBook(BookUpdateDto bookUpdateDto);
    ResponseEntity<?> deleteBook(String projectId);
    ResponseEntity<?> searchBook(String author, String title, String projectId, int pageNum, int pageSize);

}
