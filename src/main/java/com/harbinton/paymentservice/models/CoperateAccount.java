package com.harbinton.paymentservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CoperateAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountName;

    @Column(length = 10, nullable = false, unique = true)
    private String NUBAN;

    @Column(nullable = false)
    private String secretKey;

    private BigDecimal amount;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coperateAccount")
    @JsonIgnore
    private List<Transaction> transaction;
}
