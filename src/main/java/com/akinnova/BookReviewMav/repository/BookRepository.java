package com.akinnova.BookReviewMav.repository;

import com.akinnova.BookReviewMav.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Optional<List<BookEntity>> findByTitle(String title);
    Optional<List<BookEntity>> findByAuthor(String author);
    Optional<BookEntity> findByProjectId(String projectId);
}
