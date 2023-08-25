package com.akinnova.BookReviewMav.controller;

import com.akinnova.BookReviewMav.dto.bookdto.BookCreateDto;
import com.akinnova.BookReviewMav.dto.bookdto.BookResponseDto;
import com.akinnova.BookReviewMav.dto.bookdto.BookUpdateDto;
import com.akinnova.BookReviewMav.response.ResponsePojo;
import com.akinnova.BookReviewMav.service.bookservice.BookServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book/auth")

public class BookController {

    private final BookServiceImpl bookService;
    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/addBook")
    public ResponsePojo<BookResponseDto> addBook(@RequestBody BookCreateDto bookCreateDto) {
        return bookService.addBook(bookCreateDto);
    }
    @GetMapping("/allBooks")
    public ResponseEntity<?> findAllBooks(@RequestParam(defaultValue = "1") int pageNum, @RequestParam("10") int pageSize) {
        return bookService.findAllBooks(pageNum, pageSize);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<?> findBookByAuthor(@PathVariable String author, @RequestParam(defaultValue = "1") int pageNum,
                                              @RequestParam(defaultValue = "10") int pageSize) {
        return bookService.findBookByAuthor(author, pageNum, pageSize);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<?> findBookByTitle(@PathVariable String title, @RequestParam(defaultValue = "1") int pageNum,
                                             @RequestParam(defaultValue = "10") int pageSize) {
        return bookService.findBookByTitle(title, pageNum, pageSize);
    }

    @GetMapping("/projectId/{projectId}")
    public ResponseEntity<?> findBookByProjectId(@PathVariable String projectId) {
        return bookService.findBookByProjectId(projectId);
    }

    @GetMapping("/pending")
    public ResponseEntity<?> findPendingBookReview(@RequestParam(defaultValue = "1") int pageNum,
                                                   @RequestParam(defaultValue = "10") int pageSize) {
        return bookService.findPendingBookReview(pageNum, pageSize);
    }

    @GetMapping("/started")
    public ResponseEntity<?> findStartedBookReview(@RequestParam(defaultValue = "1") int pageNum,
                                                   @RequestParam(defaultValue = "10") int pageSize) {
        return bookService.findStartedBookReview(pageNum, pageSize);
    }

    @GetMapping("/completed")
    public ResponseEntity<?> findCompletedBookReview(@RequestParam(defaultValue = "1") int pageNum,
                                                     @RequestParam(defaultValue = "10") int pageSize) {
        return bookService.findCompletedBookReview(pageNum, pageSize);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateBook(@RequestBody BookUpdateDto bookUpdateDto) {
        return bookService.updateBook(bookUpdateDto);
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<?> deleteBook(@PathVariable String projectId) {
        return bookService.deleteBook(projectId);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBook(@RequestParam(required = false) String author,
                                        @RequestParam(required = false) String title,
                                        @RequestParam(required = false) String projectId,
                                        @RequestParam(defaultValue = "1") int pageNum,
                                        @RequestParam(defaultValue = "10") int pageSize) {
        return bookService.searchBook(author, title, projectId, pageNum, pageSize);
    }
}
