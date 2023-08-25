package com.akinnova.BookReviewMav.service.ratingservice;

import com.akinnova.BookReviewMav.dto.ratingdto.RatingDto;
import org.springframework.http.ResponseEntity;

public interface IRatingService {

    ResponseEntity<?> rateBook(RatingDto rateDto);
    ResponseEntity<?> titleRates(String title);
    ResponseEntity<?> allRates(int pageNum, int pageSize);

}
