package com.harbinton.paymentservice.repository;

import com.harbinton.paymentservice.models.IndividualAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualAccountRepository extends JpaRepository<IndividualAccount, Long> {
}
