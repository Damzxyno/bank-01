package com.harbinton.paymentservice.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionInitiationResponse {
        private String status;
        private String message;
        private String reference;
        private String processingUrl;
}
