package com.akinnova.BookReviewMav.dto.transactiondto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionDto {
    private String firstName;
    private String lastName;
    private String otherName;
    private String projectId;
    private String userId;
    private Double amountPaid;
    //private String invoiceCode;
    private LocalDateTime transactionDate;

}
