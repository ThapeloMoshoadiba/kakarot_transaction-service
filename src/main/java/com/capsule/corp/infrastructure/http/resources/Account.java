package com.capsule.corp.infrastructure.http.resources;

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
  String createdAt;
  String cifNumber;
  String accountNumber;
  String accountStatus;
  String initialCreditAmount;
  String closedAt;
  String reasonForClose;
}
