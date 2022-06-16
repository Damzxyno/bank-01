package com.harbinton.paymentservice.dtos.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include. NON_NULL)
public class TransactionVerificationResponse {
    private String email;
    private BigDecimal amount;
    private LocalDateTime localDateTime;
    private String status;
}
