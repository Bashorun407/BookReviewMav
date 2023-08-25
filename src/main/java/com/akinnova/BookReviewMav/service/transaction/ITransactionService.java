package com.akinnova.BookReviewMav.service.transaction;

import com.akinnova.BookReviewMav.dto.transactiondto.TransactionDto;
import org.springframework.http.ResponseEntity;

public interface ITransactionService {

    ResponseEntity<?> addTransaction(TransactionDto transactionDto);
    ResponseEntity<?> transactionByProjectId(String projectId);
    ResponseEntity<?> transactionByProviderId(String providerId, int pageNum, int pageSize);
    ResponseEntity<?> transactionByInvoiceCode(String transactionCode);

}
