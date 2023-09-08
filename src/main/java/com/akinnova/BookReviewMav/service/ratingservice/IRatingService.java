package com.akinnova.BookReviewMav.service.ratingservice;

import com.akinnova.BookReviewMav.dto.ratingdto.RatingDto;
import org.springframework.http.ResponseEntity;

public interface IRatingService {

    ResponseEntity<?> rateBook(RatingDto rateDto);
    ResponseEntity<?> serviceProviderRates(String title);
    ResponseEntity<?> allRates(int pageNum, int pageSize);

}
