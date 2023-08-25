package com.akinnova.BookReviewMav.email.emaildto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmailDetail {
    private String recipient;
    private String subject;
    private String body;
    private String filePath;

}
