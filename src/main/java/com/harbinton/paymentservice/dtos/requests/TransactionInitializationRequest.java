package com.harbinton.paymentservice.dtos.requests;

import lombok.*;
import java.math.BigDecimal;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionInitializationRequest {
    private BigDecimal amount;
}
