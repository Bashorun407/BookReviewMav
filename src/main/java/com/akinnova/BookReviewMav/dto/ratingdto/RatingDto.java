package com.akinnova.BookReviewMav.dto.ratingdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RatingDto {
    private String username;
    private Integer starRating;
}
