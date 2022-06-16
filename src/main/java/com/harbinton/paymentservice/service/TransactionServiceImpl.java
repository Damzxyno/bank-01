package com.harbinton.paymentservice.service;

import com.harbinton.paymentservice.dtos.requests.TransactionInitializationRequest;
import com.harbinton.paymentservice.dtos.requests.TransactionVerificationRequest;
import com.harbinton.paymentservice.dtos.response.TransactionInitiationResponse;
import com.harbinton.paymentservice.dtos.response.TransactionVerificationResponse;
import com.harbinton.paymentservice.models.Transaction;
import com.harbinton.paymentservice.repository.CoperateAccountRepository;
import com.harbinton.paymentservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepo;
    private final CoperateAccountRepository coperateAccountRepo;
    private final HttpServletRequest servletRequest;
    private final static String INVALID_ORGANISATION = "Coperate organization does not exist";

    @Override
    public ResponseEntity<TransactionInitiationResponse> createTransaction(TransactionInitializationRequest request) {
        boolean validSecretKey = validateSecretKey();
        if (!validSecretKey) {
            TransactionInitiationResponse response =   TransactionInitiationResponse.builder()
                    .message(INVALID_ORGANISATION)
                    .build();
            return ResponseEntity.badRequest().body(response);
        }

        String reference = generateReference();
        Transaction transaction = Transaction.builder()
                    .amount(request.getAmount())
                    .email(request.getEmail())
                    .reference(reference)
                    .status("PENDING")
                    .timeStamp(LocalDateTime.now())
                    .build();

        transactionRepo.save(transaction);

        TransactionInitiationResponse response = TransactionInitiationResponse.builder()
                    .reference(reference)
                    .message("Transaction successful")
                    .status("PENDING")
                    .build();

        return ResponseEntity.ok(response);

    }

    @Override
    public ResponseEntity<TransactionVerificationResponse> verifyTransaction(TransactionVerificationRequest request) {
        boolean validSecretKey = validateSecretKey();

        if (!validSecretKey) {
            TransactionVerificationResponse response =
                    TransactionVerificationResponse.builder()
                            .status(INVALID_ORGANISATION)
                            .build();
            return ResponseEntity.badRequest().body(response);
        }


        Optional<Transaction> transactionOptional =
                transactionRepo.findTransactionsByReference(request.getReference());

        if (transactionOptional.isPresent()){
            Transaction transaction = transactionOptional.get();
            TransactionVerificationResponse response = TransactionVerificationResponse.builder()
                    .amount(transaction.getAmount())
                    .email(transaction.getEmail())
                    .localDateTime(transaction.getTimeStamp())
                    .status(transaction.getStatus())
                    .build();
            return ResponseEntity.ok(response);
        }


        return ResponseEntity.badRequest().body(
                TransactionVerificationResponse.builder()
                        .status("NOT FOUND").build()
        );
    }

    private String generateReference(){
        return UUID.randomUUID().toString();
    }

    private boolean validateSecretKey(){
        String bearerKey = servletRequest.getHeader("Authorization");
        String key = bearerKey.substring(7);
        System.out.println(key);
        if (coperateAccountRepo.existsBySecretKey(key)) return true;
        return false;
    }
}
