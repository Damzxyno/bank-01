package com.harbinton.paymentservice.service;

import com.harbinton.paymentservice.dtos.requests.TransactionInitializationRequest;
import com.harbinton.paymentservice.dtos.response.TransactionInitiationResponse;
import com.harbinton.paymentservice.dtos.response.TransactionVerificationResponse;
import com.harbinton.paymentservice.enums.TransactionStatus;
import com.harbinton.paymentservice.enums.URLS;
import com.harbinton.paymentservice.models.CoperateAccount;
import com.harbinton.paymentservice.models.Transaction;
import com.harbinton.paymentservice.repository.CoperateAccountRepository;
import com.harbinton.paymentservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    private final static String INVALID_ORGANISATION = "Co-operate organization does not exist";

    public ResponseEntity<TransactionInitiationResponse> initiateTransaction(TransactionInitializationRequest request) {
         Optional<CoperateAccount> coperateAccountOptional = getAndValidateSecretKey();

        if (coperateAccountOptional.isEmpty()) {
            TransactionInitiationResponse response = TransactionInitiationResponse.builder()
                    .message(INVALID_ORGANISATION)
                    .build();
            return ResponseEntity.badRequest().body(response);
        }

        String reference = generateReference();

        Transaction transaction = Transaction.builder()
                    .amount(request.getAmount())
                    .reference(reference)
                    .status(TransactionStatus.PENDING)
                    .initiatedDateTime(LocalDateTime.now())
                    .coperateAccount(coperateAccountOptional.get())
                    .build();

        transactionRepo.save(transaction);

        TransactionInitiationResponse response = TransactionInitiationResponse.builder()
                    .reference(reference)
                    .message("Transaction Initiated")
                    .status("PENDING")
                    .processingUrl(URLS.TRANSACTION.getUri() + reference)
                    .build();

        return ResponseEntity.ok(response);

    }

    @Override
    public ResponseEntity<TransactionVerificationResponse> verifyTransaction(String referenceId) {
        Optional<CoperateAccount> coperateAccountOptional = getAndValidateSecretKey();

        if (coperateAccountOptional.isEmpty()) {
            TransactionVerificationResponse response =
                    TransactionVerificationResponse.builder()
                            .status(INVALID_ORGANISATION)
                            .build();
            return ResponseEntity.badRequest().body(response);
        }


        Optional<Transaction> transactionOptional =
                transactionRepo.findTransactionsByReferenceAndCoperateAccount
                        (referenceId, coperateAccountOptional.get());

        if (transactionOptional.isPresent()){
            Transaction transaction = transactionOptional.get();
            TransactionVerificationResponse response = TransactionVerificationResponse.builder()
                    .amount(transaction.getAmount())
                    .initiatedTime(transaction.getInitiatedDateTime())
                    .status(transaction.getStatus().toString())
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

    private Optional<CoperateAccount> getAndValidateSecretKey(){
        String bearerKey = servletRequest.getHeader("Authorization");
        String nuban = servletRequest.getHeader("NUBAN");
        String key = bearerKey.substring(7);

        Optional<CoperateAccount> accountOptional = coperateAccountRepo.findCoperateAccountByNUBAN(nuban);

        if (accountOptional.isPresent()){
            System.out.println(accountOptional.get());
            String secretKey = accountOptional.get().getSecretKey();
            if (passwordEncoder.matches(key, secretKey)) return  accountOptional;
        }

        return Optional.empty();
    }
}
