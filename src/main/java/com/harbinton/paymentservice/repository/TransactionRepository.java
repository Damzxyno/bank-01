package com.harbinton.paymentservice.repository;

import com.harbinton.paymentservice.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    Optional<Transaction> findTransactionsByReference(String reference);
}
