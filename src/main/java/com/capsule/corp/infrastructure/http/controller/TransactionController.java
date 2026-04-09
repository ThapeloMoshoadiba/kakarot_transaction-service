package com.capsule.corp.infrastructure.http.controller;

import com.capsule.corp.domain.service.TransactionService;
import com.capsule.corp.infrastructure.http.controller.resources.request.TransactionRequest;
import com.capsule.corp.infrastructure.http.controller.resources.response.TransactionResponse;
import com.capsule.corp.infrastructure.http.controller.resources.response.TransactionsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction-service/transactions")
// @SecurityRequirement(name = "Bearer Authentication (JWT)")
@Tag(name = "Transactions Service", description = "Handles Account Transactions")
public class TransactionController {

  // we need a JWT token to ensure that the person making calls on these endpoints is allowed to do
  // so (e.g., an employee)
  // send and receive transactions as base64 encrypted

  private final TransactionService transactionService;

  @Operation(summary = "Open Account Transaction")
  @PutMapping("/open")
  // include entityId in header to figure out source OR can we get it from JWT token?
  public ResponseEntity<TransactionResponse> openingTransaction(
      @RequestBody final TransactionRequest transactionRequest) {
    return transactionService.openingTransaction("", transactionRequest);
  }

  @Operation(summary = "Make Payment")
  @PutMapping("/pay")
  // include entityId in header to figure out source OR can we get it from JWT token?
  public ResponseEntity<TransactionResponse> payment(
      @RequestBody final TransactionRequest transactionRequest) {
    return transactionService.paymentTransaction("", transactionRequest);
  }

  @Operation(summary = "Retrieve Transactions")
  @GetMapping
  public ResponseEntity<TransactionsResponse> getTransactions(
      @RequestParam final UUID accountNumber) {
    return transactionService.getTransactions(accountNumber);
  }

  @Operation(summary = "Close Account Transaction")
  @PutMapping("/close")
  // include entityId in header to figure out source OR can we get it from JWT token?
  public ResponseEntity<TransactionResponse> closingTransaction(
      @RequestBody final TransactionRequest transactionRequest) {
    return transactionService.closingTransaction("", transactionRequest);
  }
}
