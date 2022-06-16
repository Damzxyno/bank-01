package com.harbinton.paymentservice.service;

import com.harbinton.paymentservice.dtos.requests.TransactionInitializationRequest;
import com.harbinton.paymentservice.dtos.requests.TransactionVerificationRequest;
import com.harbinton.paymentservice.dtos.response.TransactionInitiationResponse;
import com.harbinton.paymentservice.dtos.response.TransactionVerificationResponse;
import org.springframework.http.ResponseEntity;


public interface TransactionService {
    ResponseEntity<TransactionInitiationResponse> createTransaction(TransactionInitializationRequest request);
    ResponseEntity<TransactionVerificationResponse> verifyTransaction(TransactionVerificationRequest request);
}
