package com.harbinton.paymentservice.service;

import com.harbinton.paymentservice.dtos.requests.TransactionInitializationRequest;
import com.harbinton.paymentservice.models.Transaction;
import com.harbinton.paymentservice.repository.CoperateAccountRepository;
import com.harbinton.paymentservice.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Mock
    private TransactionRepository transactionRepo;

    @Mock
    private CoperateAccountRepository coperateAccountRepo;

    @Mock
    private  HttpServletRequest servletRequest;


    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionInitializationRequest transactionInitializationRequest;

    @BeforeEach
    public void setUp(){

    }


    @Test
    public void shouldSuccessfullyCreateTransaction(){
        transactionInitializationRequest = new TransactionInitializationRequest("email@email.com", BigDecimal.valueOf(1000));
        String reference = UUID.randomUUID().toString();
        Transaction transaction = Transaction.builder()
                .amount(transactionInitializationRequest.getAmount())
                .email(transactionInitializationRequest.getEmail())
                .reference(reference)
                .status("PENDING")
                .timeStamp(LocalDateTime.now())
                .build();
        Mockito.when(transactionRepo.save(any())).thenReturn(transaction);
        Mockito.when(servletRequest.getHeader(any())).thenReturn("Bearer iiii");
        Mockito.when(coperateAccountRepo.existsBySecretKey("iii")).thenReturn(true);
        transactionService.createTransaction(transactionInitializationRequest);
        verify(transactionRepo).save(any());
    }
}