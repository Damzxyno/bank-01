package com.harbinton.paymentservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harbinton.paymentservice.dtos.requests.TransactionInitializationRequest;
import com.harbinton.paymentservice.dtos.requests.TransactionVerificationRequest;
import com.harbinton.paymentservice.dtos.response.TransactionInitiationResponse;
import com.harbinton.paymentservice.dtos.response.TransactionVerificationResponse;
import com.harbinton.paymentservice.models.CoperateAccount;
import com.harbinton.paymentservice.models.Transaction;
import com.harbinton.paymentservice.repository.CoperateAccountRepository;
import com.harbinton.paymentservice.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@SpringBootTest
class TransactionControllerTest {
    @Autowired
    private TransactionController transactionController;
    @Autowired
    private CoperateAccountRepository coperateAccountRepository;
    @Autowired
    TransactionRepository transactionRepository;

    private CoperateAccount coperateAccount;
    private Transaction transaction;
    private LocalDateTime now;


    @BeforeEach
    public void setUp(){
        now = LocalDateTime.now();

        coperateAccount = CoperateAccount.builder()
                .accountName("Damzxyno FinTech")
                .amount(BigDecimal.valueOf(600_000_000))
                .secretKey("SECRETKEY")
                .build();

        coperateAccountRepository.save(coperateAccount);

        transaction = Transaction.builder()
                .amount(BigDecimal.valueOf(90_000))
                .email("dennis@gmail.com")
                .reference("THISISAREFERENCE")
                .status("PENDING")
                .build();
        transactionRepository.save(transaction);



    }

    @AfterEach
    public void breakDown(){
        coperateAccountRepository.deleteAll();
        transactionRepository.deleteAll();
    }

    @DisplayName("Test for successful Initialization of Transaction")
    @Test
    public void shouldReturnCorrectResponseEntity() throws Exception {
        TransactionInitializationRequest request =
                TransactionInitializationRequest.builder()
                        .amount(BigDecimal.valueOf(30_000))
                        .email("gbenga@gmail.com")
                        .build();

        TransactionInitiationResponse response =
                TransactionInitiationResponse.builder()
                        .message("")
                        .reference("")
                        .status("PENDING")
                        .build();


        String requestContent = new ObjectMapper().writeValueAsString(request);
        String responseContent = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/initialize")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer SECRETKEY")
                .content(requestContent);



        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.transactionController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @DisplayName("Test for successful Verification of Transaction")
    @Test
    public void shouldReturnTransactionResponseEntity() throws Exception {
        TransactionVerificationRequest request =
                TransactionVerificationRequest.builder()
                        .reference("THISISAREFERENCE")
                        .build();

        TransactionVerificationResponse response =
                TransactionVerificationResponse.builder()
                        .email("dennis@gmail.com")
                        .status("PENDING")
                        .build();


        String requestContent = new ObjectMapper().writeValueAsString(request);
        String responseContent = new ObjectMapper().writeValueAsString(response);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/verify")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer SECRETKEY")
                .content(requestContent);



        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.transactionController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().json(responseContent));
    }
}