package com.capsule.corp.infrastructure.http.resources;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "balance")
public class Balance {

  @Id
  @Column(name = "id", nullable = false, updatable = false)
  UUID id;

  LocalDateTime createdAt;
  LocalDateTime updatedAt;
  UUID accountNumber;
  BigDecimal balance;
}
