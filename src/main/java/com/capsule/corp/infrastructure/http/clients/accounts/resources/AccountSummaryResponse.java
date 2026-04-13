package com.capsule.corp.infrastructure.http.clients.accounts.resources;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountSummaryResponse {
  private UUID accountNumber;
  private String accountStatus;
  private BigDecimal initialCreditAmount;

  private String reason;
  private boolean success;
}
