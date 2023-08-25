package com.akinnova.BookReviewMav.email.emailservice;

import com.akinnova.BookReviewMav.email.emaildto.EmailDetail;
import org.springframework.http.ResponseEntity;

public interface IEmailService {
    ResponseEntity<?> sendSimpleEmail(EmailDetail emailDetail);
    ResponseEntity<?> sendEmailWithAttachment(EmailDetail emailDetail);

}

