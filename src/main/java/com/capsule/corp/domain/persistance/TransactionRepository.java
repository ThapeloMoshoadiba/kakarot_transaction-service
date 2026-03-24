package com.capsule.corp.domain.persistance;

import com.capsule.corp.infrastructure.http.resources.Transaction;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

  Optional<List<Transaction>> findByAccountNumber(UUID accountNumber);
}
