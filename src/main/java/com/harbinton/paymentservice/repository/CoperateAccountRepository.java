package com.harbinton.paymentservice.repository;

import com.harbinton.paymentservice.models.CoperateAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoperateAccountRepository extends JpaRepository<CoperateAccount, Long> {
    Optional<CoperateAccount> findCoperateAccountByNUBAN(String nuban);
}
