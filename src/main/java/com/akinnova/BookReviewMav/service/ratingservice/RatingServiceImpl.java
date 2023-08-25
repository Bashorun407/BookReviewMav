package com.akinnova.BookReviewMav.service.ratingservice;

import com.akinnova.BookReviewMav.dto.ratingdto.RatingDto;
import com.akinnova.BookReviewMav.entity.Rating;
import com.akinnova.BookReviewMav.enums.ResponseType;
import com.akinnova.BookReviewMav.exception.ApiException;
import com.akinnova.BookReviewMav.repository.RatingRepository;
import com.akinnova.BookReviewMav.response.ResponsePojo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements IRatingService {

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public ResponseEntity<?> rateBook(RatingDto rateDto) {
        //If user has reviewed a book before, retrieve the exact record and edit
        Rating userRate = ratingRepository.findByTitle(rateDto.getTitle())
                .orElse(ratingRepository.save(Rating.builder()
                        .title(rateDto.getTitle())
                        .starRating(rateDto.getStarRating())
                        .rateCount((long)1)
                        .averageRating((double)rateDto.getStarRating())
                        .rateTime(LocalDateTime.now())
                        .build()));
        //obtains the current average rating
        double currentAverage = userRate.getAverageRating();
        //obtains the current rate counts (i.e. number of rates given)
        long currentCount = userRate.getRateCount();

        userRate.setStarRating(rateDto.getStarRating());
        userRate.setRateCount(userRate.getRateCount() + 1);
        //To calculate the new average: Product of former average and former count summed with new rating and all
        //...divided with new count (i.e. former count + 1
        userRate.setAverageRating(((currentAverage * currentCount) + rateDto.getStarRating())
                / (currentCount + 1));
        //Save update into the database
        ratingRepository.save(userRate);

        return new ResponseEntity<>("Thanks for your review", HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<?> titleRates(String title) {

        //To retrieve all reviews for a book by title
        Rating rateBook = ratingRepository.findByTitle(title)
                .orElseThrow(()->
                        new ApiException(String.format("There are no reviews for book with this title %s: yet", title)));

        return new ResponseEntity<>(String.format("Rate for book title %s: %f from %d users", title, rateBook.getAverageRating(),
                rateBook.getRateCount() ), HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<?> allRates(int pageNum, int pageSize) {
        //To retrieve all reviews in the review database
        List<Rating> rateBookList = ratingRepository.findAll().stream().skip(pageNum - 1).limit(pageSize).toList();

        if(rateBookList.isEmpty())
            return new ResponseEntity<>("There are no reviews yet", HttpStatus.NO_CONTENT);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All rates", rateBookList));
    }

}
