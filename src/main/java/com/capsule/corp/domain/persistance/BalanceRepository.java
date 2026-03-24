package com.capsule.corp.domain.persistance;

import com.capsule.corp.infrastructure.http.resources.Balance;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, UUID> {

  Optional<Balance> findByAccountNumber(UUID accountNumber);
}
