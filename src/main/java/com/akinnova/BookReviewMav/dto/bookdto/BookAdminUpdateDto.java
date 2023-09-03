package com.akinnova.BookReviewMav.dto.bookdto;

import com.akinnova.BookReviewMav.enums.ProjectApproval;
import lombok.Data;

@Data
public class BookAdminUpdateDto {
    private String projectId;
    private ProjectApproval projectApproval;
}
