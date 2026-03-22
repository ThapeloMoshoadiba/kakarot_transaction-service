package com.capsule.corp.domain.persistance;

import com.capsule.corp.infrastructure.http.controller.resources.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    Optional<Transaction> findByAccountNumber(String accountNumber); // This should return a list
}
