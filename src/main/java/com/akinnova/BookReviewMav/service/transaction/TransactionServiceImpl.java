package com.akinnova.BookReviewMav.service.transaction;

import com.akinnova.BookReviewMav.dto.transactiondto.TransactionDto;
import com.akinnova.BookReviewMav.dto.transactiondto.TransactionResponseDto;
import com.akinnova.BookReviewMav.email.emaildto.EmailDetail;
import com.akinnova.BookReviewMav.email.emailservice.EmailServiceImpl;
import com.akinnova.BookReviewMav.entity.BookEntity;
import com.akinnova.BookReviewMav.entity.Transaction;
import com.akinnova.BookReviewMav.entity.UserEntity;
import com.akinnova.BookReviewMav.enums.ResponseType;
import com.akinnova.BookReviewMav.enums.ReviewStatus;
import com.akinnova.BookReviewMav.exception.ApiException;
import com.akinnova.BookReviewMav.repository.BookRepository;
import com.akinnova.BookReviewMav.repository.TransactionRepository;
import com.akinnova.BookReviewMav.repository.UserRepository;
import com.akinnova.BookReviewMav.response.ResponsePojo;
import com.akinnova.BookReviewMav.response.ResponseUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements ITransactionService{

    private EmailServiceImpl emailService;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    public ResponseEntity<?> addTransaction(TransactionDto transactionDto) {
        //Transaction has been created and saved to repository
        transactionRepository.save(Transaction.builder()
                .firstName(transactionDto.getFirstName())
                .lastName(transactionDto.getLastName())
                .otherName(transactionDto.getOtherName())
                .projectId(transactionDto.getProjectId())
                .userId(transactionDto.getUserId())
                .amountPaid(transactionDto.getAmountPaid())
                .invoiceCode(ResponseUtils.generateInvoiceCode(8, transactionDto.getInvoiceCode()))
                .transactionDate(LocalDateTime.now())
                .build());

        //Once amount has been paid for a project, project is activated
        BookEntity bookEntity = bookRepository.findByProjectId(transactionDto.getProjectId())
                .orElseThrow(()-> new ApiException("There is no project by this id: " + transactionDto.getProjectId()));

        //Update and Save changes to repository
        bookEntity.setReviewStatus(ReviewStatus.Started);
        bookRepository.save(bookEntity);

        //Then notification is sent to Service provider selected by user
        UserEntity userEntity = userRepository.findByUserId(transactionDto.getUserId())
                .orElseThrow(()-> new ApiException("There is no Service provider with id: " + transactionDto.getUserId()));

        //Email body content
        String emailBody = "Dear " + userEntity.getLastName() + ", " + userEntity.getFirstName() + "."
                + "\n This is to notify you that you can now commence on the task assigned to you."
                + "\n Payment will be upon completion and feedback by client."
                + "\n Best regards.";

        //Email object preparation
        EmailDetail emailDetail = EmailDetail.builder()
                .subject("Project Commencement Notification")
                .body(emailBody)
                .recipient(userEntity.getEmail())
                .build();

        //Email sent here
        emailService.sendSimpleEmail(emailDetail);

        return ResponseEntity.ok("Transaction done successfully");
    }

    @Override
    public ResponseEntity<?> transactionByProjectId(String projectId) {
        Transaction transaction = transactionRepository.findByProjectId(projectId)
                .orElseThrow(()-> new ApiException("There is no transaction with project id: " + projectId));

        return ResponseEntity.ok(transaction);
    }

    @Override
    public ResponseEntity<?> transactionByProviderId(String providerId, int pageNum, int pageSize) {

        List<Transaction> transactionList = transactionRepository.findByUserId(providerId)
                .orElseThrow(()-> new ApiException("There are no transaction with provider id: " + providerId));

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, String.format("Transaction by id: %s", providerId),
                transactionList.stream().skip(pageNum - 1).limit(pageSize).map(TransactionResponseDto::new)));
    }

    @Override
    public ResponseEntity<?> transactionByInvoiceCode(String transactionCode) {

        Transaction transaction = transactionRepository.findByInvoiceCode(transactionCode)
                .orElseThrow(()-> new ApiException("There is no transaction with transaction code: " + transactionCode));

        return ResponseEntity.ok(transaction);
    }
}
