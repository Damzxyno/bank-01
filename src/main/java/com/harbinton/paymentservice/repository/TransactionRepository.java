package com.harbinton.paymentservice.repository;

import com.harbinton.paymentservice.models.CoperateAccount;
import com.harbinton.paymentservice.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    @Query("SELECT t  FROM Transaction t WHERE t.reference = :reference AND t.coperateAccount = :coperateAccount")
    Optional<Transaction> findTransactionsByReferenceAndCoperateAccount(String reference, CoperateAccount coperateAccount);
}
