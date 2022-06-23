package com.harbinton.paymentservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harbinton.paymentservice.dtos.requests.TransactionInitializationRequest;
import com.harbinton.paymentservice.dtos.response.TransactionInitiationResponse;
import com.harbinton.paymentservice.dtos.response.TransactionVerificationResponse;
import com.harbinton.paymentservice.enums.TransactionStatus;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private TransactionRepository transactionRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    public void setUp(){
        LocalDateTime now = LocalDateTime.now();

        CoperateAccount coperateAccount = CoperateAccount.builder()
                .accountName("Damzxyno FinTech")
                .NUBAN("0012305439")
                .amount(BigDecimal.valueOf(6_000))
                .secretKey(passwordEncoder.encode("SECRETKEY"))
                .build();

        coperateAccountRepository.save(coperateAccount);

        Transaction transaction = Transaction.builder()
                .coperateAccount(coperateAccount)
                .amount(BigDecimal.valueOf(90_000))
                .reference("THISISAREFERENCE")
                .status(TransactionStatus.PENDING)
                .build();
        transactionRepository.save(transaction);



    }

    @AfterEach
    public void breakDown(){
        transactionRepository.deleteAll();
        coperateAccountRepository.deleteAll();
    }

    @DisplayName("Test for successful Initialization of Transaction")
    @Test
    public void shouldReturnCorrectResponseEntity() throws Exception {
        TransactionInitializationRequest request =
                TransactionInitializationRequest.builder()
                        .amount(BigDecimal.valueOf(30_000))
                        .build();

        TransactionInitiationResponse response =
                TransactionInitiationResponse.builder()
                        .message("")
                        .reference("")
                        .status("PENDING")
                        .build();


        String requestContent = new ObjectMapper().writeValueAsString(request);
        String responseContent = new ObjectMapper().writeValueAsString(request);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/initiate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer SECRETKEY")
                .header("NUBAN", "0012305439")
                .content(requestContent);



        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.transactionController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @DisplayName("Test for successful Verification of Transaction")
    @Test
    public void shouldReturnTransactionResponseEntity() throws Exception {
        String reference = "THISISAREFERENCE";

        TransactionVerificationResponse response =
                TransactionVerificationResponse.builder()
                        .status("Pending")
                        .build();


        String responseContent = new ObjectMapper().writeValueAsString(response);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/verify/" + reference)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer SECRETKEY")
                .header("NUBAN", "0012305439");



        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.transactionController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().json(responseContent));
    }
}