package com.akinnova.BookReviewMav.dto.transactiondto;

import com.akinnova.BookReviewMav.entity.Transaction;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TransactionResponseDto {
    private String firstName;
    private String lastName;
    private String otherName;
    private Double amountPaid;
    private String invoiceCode;
    private LocalDateTime transactionDate;

    public TransactionResponseDto(Transaction transaction){
        this.firstName = transaction.getFirstName();
        this.lastName = transaction.getLastName();
        this.otherName = transaction.getOtherName();
        this.amountPaid = transaction.getAmountPaid();
        this.invoiceCode = transaction.getInvoiceCode();
        this.transactionDate = transaction.getTransactionDate();
    }
}
