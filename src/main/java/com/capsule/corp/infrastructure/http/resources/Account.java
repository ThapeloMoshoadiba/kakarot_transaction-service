package com.capsule.corp.infrastructure.http.resources;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

  String accountId;
  LocalDateTime createdAt;
  String cifNumber;
  UUID accountNumber;

  String accountStatus; // might need the accountStatus enum

  BigDecimal initialCreditAmount;

  LocalDateTime blockedAt;
  String reasonForBlock;

  LocalDateTime unblockedAt;
  String reasonForUnblock;

  LocalDateTime closedAt;
  String reasonForClose;
}
