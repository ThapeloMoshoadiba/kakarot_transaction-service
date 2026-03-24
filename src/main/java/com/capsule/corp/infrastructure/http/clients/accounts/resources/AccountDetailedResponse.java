package com.capsule.corp.infrastructure.http.clients.accounts.resources;

import com.capsule.corp.infrastructure.http.resources.Account;
import com.capsule.corp.infrastructure.http.resources.ClientDetails;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDetailedResponse {
  ClientDetails clientDetails;
  Account account;
  String reason;
  boolean success;
}
