package com.harbinton.paymentservice.repository;

import com.harbinton.paymentservice.models.CoperateAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoperateAccountRepository extends JpaRepository<CoperateAccount, Long> {
    boolean existsBySecretKey(String secretKey);
}
