package com.capsule.corp.infrastructure.http.clients.accounts.resources;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountSummaryResponse {
  UUID accountNumber;
  String accountStatus;
  BigDecimal initialCreditAmount;

  String reason;
  boolean success;
}
