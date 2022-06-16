package com.harbinton.paymentservice.dtos.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionVerificationRequest {
    private String reference;
}
