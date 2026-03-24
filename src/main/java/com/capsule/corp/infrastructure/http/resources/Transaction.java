package com.capsule.corp.infrastructure.http.resources;

import com.capsule.corp.infrastructure.http.resources.enums.EntryType;
import com.capsule.corp.infrastructure.http.resources.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

  @Id
  @Column(name = "transaction_id", nullable = false, updatable = false)
  UUID transactionId;

  UUID accountNumber;
  LocalDateTime timestamp;
  BigDecimal amount;
  String initiator;

  @Enumerated(EnumType.STRING)
  TransactionType transactionType;

  @Enumerated(EnumType.STRING)
  EntryType entryType;
}
