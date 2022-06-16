package com.harbinton.paymentservice.controllers;

import com.harbinton.paymentservice.dtos.requests.TransactionInitializationRequest;
import com.harbinton.paymentservice.dtos.requests.TransactionVerificationRequest;
import com.harbinton.paymentservice.dtos.response.TransactionInitiationResponse;
import com.harbinton.paymentservice.dtos.response.TransactionVerificationResponse;
import com.harbinton.paymentservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/initialize")
    public ResponseEntity<TransactionInitiationResponse>
    initializeTransaction(@RequestBody TransactionInitializationRequest request) {
        return transactionService.createTransaction(request);
    }

    @PostMapping("/verify")
    public ResponseEntity<TransactionVerificationResponse>
    verifyTransaction(@RequestBody TransactionVerificationRequest verificationRequest) {
        return transactionService.verifyTransaction(verificationRequest);
    }
}
