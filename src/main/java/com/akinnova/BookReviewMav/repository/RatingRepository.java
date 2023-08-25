package com.akinnova.BookReviewMav.repository;

import com.akinnova.BookReviewMav.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByTitle(String title);
}
