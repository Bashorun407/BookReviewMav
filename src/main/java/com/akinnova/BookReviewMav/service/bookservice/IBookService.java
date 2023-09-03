package com.akinnova.BookReviewMav.service.bookservice;

import com.akinnova.BookReviewMav.dto.bookdto.*;
import com.akinnova.BookReviewMav.response.ResponsePojo;
import org.springframework.http.ResponseEntity;

public interface IBookService {
    ResponsePojo<BookResponseDto> addBook(BookCreateDto bookCreateDto);
    ResponseEntity<?> findAllBooks(int pageNum, int pageSize);
    ResponseEntity<?> findBookByOwner(String author, int pageNum, int pageSize);
    ResponseEntity<?> findBookByTitle(String title, int pageNum, int pageSize);
    ResponseEntity<?> findBookByProjectId(String projectId);
    ResponseEntity<?> findPendingBookReview(int pageNum, int pageSize);
    ResponseEntity<?> findStartedBookReview(int pageNum, int pageSize);
    ResponseEntity<?> findCompletedBookReview(int pageNum, int pageSize);
    ResponseEntity<?> updateBook(BookUpdateDto bookUpdateDto);
    ResponseEntity<?> serviceProviderBookUpdate(BookServiceProviderUpdateDto serviceProviderUpdateDto);
    ResponseEntity<?> adminBookUpdate(BookAdminUpdateDto adminUpdateDto);
    ResponseEntity<?> deleteBook(String projectId);
    ResponseEntity<?> searchBook(String author, String title, String projectId, int pageNum, int pageSize);

}
