package com.harbinton.paymentservice.models;

import com.harbinton.paymentservice.enums.TransactionStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private CoperateAccount coperateAccount;

    @ManyToOne
    private IndividualAccount individualAccount;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(unique = true)
    private String reference;


    @CreationTimestamp
    private LocalDateTime initiatedDateTime;

    private LocalDateTime finalizedDateTime;

}
