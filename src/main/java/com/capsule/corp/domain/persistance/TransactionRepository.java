package com.capsule.corp.domain.persistance;

import com.capsule.corp.infrastructure.http.resources.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    Optional<List<Transaction>> findByAccountNumber(String accountNumber);
}
