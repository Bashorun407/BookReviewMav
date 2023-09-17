package com.akinnova.BookReviewMav.entitypaging;

import org.springframework.data.domain.Sort;

public class UserPage {
    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "lastName";
}
