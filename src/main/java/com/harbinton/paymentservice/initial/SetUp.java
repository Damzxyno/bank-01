package com.harbinton.paymentservice.initial;

import com.harbinton.paymentservice.models.CoperateAccount;
import com.harbinton.paymentservice.models.IndividualAccount;
import com.harbinton.paymentservice.repository.CoperateAccountRepository;
import com.harbinton.paymentservice.repository.IndividualAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SetUp {
    private final BCryptPasswordEncoder encoder;
    private final CoperateAccountRepository coperateAccountRepository;
    private final IndividualAccountRepository individualAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner commandLineRunner (){
        return args -> {
            log.info("Prefilled database with Accounts");
            coperateAccountRepository.saveAll(getCoperateAccounts());
            individualAccountRepository.saveAll(getIndividualAccounts());
            log.info("Database filled!");
        };
    }

    public List<CoperateAccount> getCoperateAccounts(){
        return List.of(
                CoperateAccount.builder()
                        .amount(BigDecimal.valueOf(3_000_000))
                        .secretKey(passwordEncoder.encode("SECRETKEY"))
                        .accountName("Damzxyno International Store")
                        .NUBAN("0978654324")
                        .creationDate(LocalDateTime.of(2012, Month.JANUARY, 22, 12, 0))
                        .build(),

                CoperateAccount.builder()
                        .amount(BigDecimal.valueOf(8_000_000))
                        .secretKey(passwordEncoder.encode("SECRETKEY"))
                        .accountName("Gbenga and Co International ")
                        .NUBAN("0973334324")
                        .creationDate(LocalDateTime.of(2022, Month.JUNE, 23, 12, 30))
                        .build(),

                CoperateAccount.builder()
                        .amount(BigDecimal.valueOf(9_000_000))
                        .secretKey(passwordEncoder.encode("SECRETKEY"))
                        .accountName("Lagos State University")
                        .NUBAN("0111654324")
                        .creationDate(LocalDateTime.of(1999, Month.SEPTEMBER, 12, 12, 00))
                        .build()
                );
    }

    public List<IndividualAccount> getIndividualAccounts(){
        return List.of(
                IndividualAccount.builder()
                        .accountName("Oluwole Damilola")
                        .NUBAN("0123432123")
                        .amount(BigDecimal.valueOf(40_000))
                        .build(),

                IndividualAccount.builder()
                        .accountName("Okoroafor Somtochukwu")
                        .NUBAN("0923432128")
                        .amount(BigDecimal.valueOf(90_000))
                        .build()
        );
    }

}
