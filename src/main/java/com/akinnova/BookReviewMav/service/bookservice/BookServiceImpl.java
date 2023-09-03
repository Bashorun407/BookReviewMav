package com.akinnova.BookReviewMav.service.bookservice;

import com.akinnova.BookReviewMav.dto.bookdto.*;
import com.akinnova.BookReviewMav.entity.BookEntity;
import com.akinnova.BookReviewMav.enums.ResponseType;
import com.akinnova.BookReviewMav.exception.ApiException;
import com.akinnova.BookReviewMav.repository.BookRepository;
import com.akinnova.BookReviewMav.response.ResponsePojo;
import com.akinnova.BookReviewMav.response.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.akinnova.BookReviewMav.enums.ProjectApproval.NOT_APPROVED;
import static com.akinnova.BookReviewMav.enums.ProjectCompletion.*;

@Service
public class BookServiceImpl implements IBookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public ResponsePojo<BookResponseDto> addBook(BookCreateDto bookCreateDto) {

        //Create and save new object
        BookEntity bookEntity =  bookRepository.save(BookEntity.builder()
                .coverImage(bookCreateDto.getCoverImage())
                .title(bookCreateDto.getTitle())
                .category(bookCreateDto.getCategory())
                .username(bookCreateDto.getUsername())
                .content(bookCreateDto.getContent())
                .projectId(ResponseUtils.generateBookSerialNumber(10, bookCreateDto.getTitle()))
                .projectApproval(NOT_APPROVED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        return new ResponsePojo<>(ResponseType.SUCCESS, "Book added successfully",new BookResponseDto(bookEntity));
    }

    @Override
    public ResponseEntity<?> findAllBooks(int pageNum, int pageSize) {

        List<BookEntity> bookEntityList = bookRepository.findAll();

        if (bookEntityList.isEmpty())
            return new ResponseEntity<>("There are no projects currently", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All books", bookEntityList.stream()
                .filter(BookEntity::getActiveStatus).sorted(Comparator.comparing(BookEntity::getTitle)).skip(pageNum - 1).limit(pageSize).map(BookResponseDto::new)));
    }

    @Override
    public ResponseEntity<?> findBookByOwner(String username, int pageNum, int pageSize) {

        List<BookEntity> bookEntityList = bookRepository.findByUsername(username)
                .orElseThrow(()-> new ApiException("There are no projects by this author: " + username));

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All books", bookEntityList.stream()
                .filter(BookEntity::getActiveStatus).sorted(Comparator.comparing(BookEntity::getTitle)).skip(pageNum - 1).limit(pageSize).map(BookResponseDto::new)));
    }

    @Override
    public ResponseEntity<?> findBookByTitle(String title, int pageNum, int pageSize) {

        List<BookEntity> bookEntityList = bookRepository.findByTitle(title)
                .orElseThrow(()-> new ApiException("There are no projects by this title: " + title));

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All books", bookEntityList.stream()
                .filter(BookEntity::getActiveStatus).sorted(Comparator.comparing(BookEntity::getTitle)).skip(pageNum - 1).limit(pageSize).map(BookResponseDto::new)));
    }

    @Override
    public ResponseEntity<?> findBookByProjectId(String projectId) {

        BookEntity bookEntity = bookRepository.findByProjectId(projectId)
                .orElseThrow(()-> new ApiException("There are no projects with this project id: " + projectId));

        return ResponseEntity
                .ok(new ResponsePojo<>(ResponseType.SUCCESS, "Book by project id: " + projectId, new BookResponseDto(bookEntity)));
    }

    // TODO: 13/08/2023 To complete the following methods
    @Override
    public ResponseEntity<?> findPendingBookReview(int pageNum, int pageSize) {
        List<BookEntity> bookEntityList = bookRepository.findAll().stream()
                .filter(BookEntity::getActiveStatus)
                .filter(x-> x.getProjectCompletion() == PENDING)
                .sorted(Comparator.comparing(BookEntity::getTitle))
                .toList();

        if(bookEntityList.isEmpty())
            return new ResponseEntity<>("There are no pending reviews yet.", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Pending reviews", bookEntityList.stream()
                .skip(pageNum - 1).limit(pageSize).map(BookResponseDto::new)));
    }

    @Override
    public ResponseEntity<?> findStartedBookReview(int pageNum, int pageSize) {
        List<BookEntity> bookEntityList = bookRepository.findAll().stream()
                .filter(BookEntity::getActiveStatus)
                .filter(x-> x.getProjectCompletion() == STARTED)
                .toList();

        if(bookEntityList.isEmpty())
            return new ResponseEntity<>("There are no 'started' reviews yet.", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Started reviews", bookEntityList.stream()
                .skip(pageNum - 1).limit(pageSize).map(BookResponseDto::new)));
    }

    @Override
    public ResponseEntity<?> findCompletedBookReview(int pageNum, int pageSize) {
        List<BookEntity> bookEntityList = bookRepository.findAll().stream()
                .filter(BookEntity::getActiveStatus)
                .filter(x-> x.getProjectCompletion() == COMPLETED)
                .sorted(Comparator.comparing(BookEntity::getTitle))
                .toList();

        if(bookEntityList.isEmpty())
            return new ResponseEntity<>("There are no completed reviews yet.", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Completed reviews", bookEntityList.stream()
                .skip(pageNum - 1).limit(pageSize).map(BookResponseDto::new)));
    }

    @Override
    public ResponseEntity<?> updateBook(BookUpdateDto bookUpdateDto) {
        BookEntity bookEntity = bookRepository.findByProjectId(bookUpdateDto.getProjectId())
                .orElseThrow(()-> new ApiException(String.format("Book with projectId: %s does not exist",
                        bookUpdateDto.getProjectId())));

        bookEntity.setCoverImage(bookUpdateDto.getCoverImage());
        bookEntity.setTitle(bookUpdateDto.getTitle());
        bookEntity.setUsername(bookEntity.getUsername());
        bookEntity.setActiveStatus(true);
        bookEntity.setModifiedOn(LocalDateTime.now());

        //Save to repository
        bookRepository.save(bookEntity);

        return ResponseEntity.ok("Book updated successfully");
    }

    @Override
    public ResponseEntity<?> serviceProviderBookUpdate(BookServiceProviderUpdateDto serviceProviderUpdateDto) {
        BookEntity bookEntity = bookRepository.findByProjectId(serviceProviderUpdateDto.getProjectId())
                .orElseThrow(()-> new ApiException(String.format("Book with projectId: %s does not exist",
                        serviceProviderUpdateDto.getProjectId())));

        bookEntity.setServiceProvider(serviceProviderUpdateDto.getServiceProvider());
        bookEntity.setProjectCompletion(serviceProviderUpdateDto.getProjectCompletion());
        bookEntity.setStartDate(serviceProviderUpdateDto.getStartDate());
        bookEntity.setEndDate(serviceProviderUpdateDto.getEndDate());
        bookEntity.setModifiedOn(LocalDateTime.now());

        bookRepository.save(bookEntity);

        return ResponseEntity.ok("Book updated successfully");

    }

    @Override
    public ResponseEntity<?> adminBookUpdate(BookAdminUpdateDto adminUpdateDto) {
        BookEntity bookEntity = bookRepository.findByProjectId(adminUpdateDto.getProjectId())
                .orElseThrow(()-> new ApiException(String.format("Book with projectId: %s does not exist",
                        adminUpdateDto.getProjectId())));

        bookEntity.setProjectApproval(adminUpdateDto.getProjectApproval());

        bookRepository.save(bookEntity);

        return ResponseEntity.ok("Book updated successfully");
    }

    @Override
    public ResponseEntity<?> deleteBook(String projectId) {
        BookEntity bookEntity = bookRepository.findByProjectId(projectId)
                .filter(BookEntity::getActiveStatus)
                .orElseThrow(()-> new ApiException(String.format("Book with this serialNumber: %s does not exist",
                        projectId)));

        //Change the active status of the 'deleted' book
        bookEntity.setActiveStatus(false);
        //Save to repository
        bookRepository.save(bookEntity);
        return ResponseEntity.ok("Book has been deleted");
    }

    @Override
    public ResponseEntity<?> searchBook(String username, String title, String projectId, int pageNum, int pageSize) {
        List<BookEntity> bookEntity = new ArrayList<>();

        if(StringUtils.hasText(username))
            bookEntity = bookRepository.findByUsername(username)
                    .orElseThrow(()-> new ApiException(String.format("There are no books by this author: %s", username)));

        if(StringUtils.hasText(title))
            bookEntity = bookRepository.findByTitle(title)
                    .orElseThrow(()-> new ApiException(String.format("There are no books by this title: %s", title)));

        if(StringUtils.hasText(projectId))
            bookEntity.add(bookRepository.findByProjectId(projectId).filter(BookEntity::getActiveStatus)
                    .orElseThrow(()-> new ApiException(String.format("There are no books with this serial number: %s", projectId))));

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Completed reviews", bookEntity.stream()
                .filter(BookEntity::getActiveStatus)
                .skip(pageNum).limit(pageSize).map(BookResponseDto::new)));

    }

}
