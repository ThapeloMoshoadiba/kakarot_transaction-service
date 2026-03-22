package com.capsule.corp.infrastructure.http.controller.resources;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public class Transaction {
    UUID transactionId;
    String accountNumber;
    LocalDate timestamp;
    BigDecimal amount;
    String initiator;
}
