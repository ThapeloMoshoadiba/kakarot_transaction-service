package com.capsule.corp.infrastructure.http.clients.accounts.resources;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicAccountRequest {
  private UUID accountNumber;
  private String reason;
}
