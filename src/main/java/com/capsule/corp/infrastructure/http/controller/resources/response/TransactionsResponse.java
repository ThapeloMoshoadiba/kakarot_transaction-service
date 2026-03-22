package com.capsule.corp.infrastructure.http.controller.resources.response;

import com.capsule.corp.infrastructure.http.controller.resources.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsResponse {

    List<Transaction> transactions;
    BigDecimal balance;
}
