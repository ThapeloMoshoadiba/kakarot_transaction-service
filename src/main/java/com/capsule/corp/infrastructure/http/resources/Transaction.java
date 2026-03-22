package com.capsule.corp.infrastructure.http.resources;

import com.capsule.corp.infrastructure.http.resources.enums.EntryType;
import com.capsule.corp.infrastructure.http.resources.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    UUID transactionId;
    String accountNumber;
    TransactionType transactionType;
    EntryType entryType;
    LocalDate timestamp;
    BigDecimal amount;
    String initiator;
}
