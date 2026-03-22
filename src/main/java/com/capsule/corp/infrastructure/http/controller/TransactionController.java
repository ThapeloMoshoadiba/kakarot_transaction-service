package com.capsule.corp.infrastructure.http.controller;

import com.capsule.corp.domain.service.TransactionService;
import com.capsule.corp.infrastructure.http.controller.resources.Balance;
import com.capsule.corp.infrastructure.http.controller.resources.Transaction;
import com.capsule.corp.infrastructure.http.controller.resources.request.PaymentRequest;
import com.capsule.corp.infrastructure.http.controller.resources.response.PaymentResponse;
import com.capsule.corp.infrastructure.http.controller.resources.response.TransactionsResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction-service/transactions")
//@SecurityRequirement(name = "Bearer Authentication (JWT)")
@Tag(name = "Transactions Service", description = "Handles Account Transactions")
public class TransactionController {

    // we need a JWT token to ensure that the person making calls on these endpoints is allowed to do so (e.g., an employee)

    private final TransactionService transactionService;

    @Operation(summary = "Make Payment")
    @PutMapping
    // include entityId in header to figure out source OR can we get it from JWT token?
    public PaymentResponse payment(@RequestBody final PaymentRequest paymentRequest) {
        return transactionService.payment("", paymentRequest);
    }

    @Operation(summary = "Retrieve Transactions")
    @GetMapping
    public TransactionsResponse getTransactions(@RequestParam final String accountNumber) {
        return transactionService.getTransactions(accountNumber);
    }
}
