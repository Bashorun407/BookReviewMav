package com.akinnova.BookReviewMav.repository;

import com.akinnova.BookReviewMav.entity.Transaction;
import com.akinnova.BookReviewMav.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByProjectId(String serialNumber);
    Optional<List<Transaction>> findByUserId(String providerId);
    Optional<Transaction> findByInvoiceCode(String invoiceCode);


}
