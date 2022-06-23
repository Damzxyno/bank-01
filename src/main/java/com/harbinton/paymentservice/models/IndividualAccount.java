package com.harbinton.paymentservice.models;

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
public class IndividualAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountName;

    @Column(length = 10, nullable = false, unique = true)
    private String NUBAN;

    private BigDecimal amount;

    @CreationTimestamp
    private LocalDateTime creationDate;
}
