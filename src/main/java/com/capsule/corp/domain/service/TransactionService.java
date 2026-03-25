package com.capsule.corp.domain.service;

import com.capsule.corp.common.exception.BalanceNotFoundException;
import com.capsule.corp.common.exception.TransactionsNotFoundException;
import com.capsule.corp.domain.mapper.TransactionMapper;
import com.capsule.corp.domain.persistance.BalanceRepository;
import com.capsule.corp.domain.persistance.TransactionRepository;
import com.capsule.corp.domain.validation.rules.PaymentRules;
import com.capsule.corp.infrastructure.http.clients.accounts.AccountServiceClient;
import com.capsule.corp.infrastructure.http.clients.accounts.resources.AccountDetailedResponse;
import com.capsule.corp.infrastructure.http.controller.resources.request.TransactionRequest;
import com.capsule.corp.infrastructure.http.controller.resources.response.TransactionResponse;
import com.capsule.corp.infrastructure.http.controller.resources.response.TransactionsResponse;
import com.capsule.corp.infrastructure.http.resources.Balance;
import com.capsule.corp.infrastructure.http.resources.Transaction;
import com.capsule.corp.infrastructure.http.resources.enums.EntryType;
import com.capsule.corp.infrastructure.http.resources.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

  private final PaymentRules paymentRules;
  private final BalanceRepository balanceRepository;
  private final TransactionMapper transactionMapper;
  private final AccountServiceClient accountServiceClient;
  private final TransactionRepository transactionRepository;

  Balance balance;

  public TransactionResponse openingTransaction(
      String entityId, TransactionRequest transactionRequest) {
    UUID transactionId = UUID.randomUUID();
    try {
      AccountDetailedResponse account =
          accountServiceClient.getAccount(transactionRequest.getAccountNumber());
      Optional<Balance> balance =
          balanceRepository.findByAccountNumber(transactionRequest.getAccountNumber());
      Optional<Transaction> transaction =
          transactionRepository.findByAccountNumberAndTransactionType(
              transactionRequest.getAccountNumber(), TransactionType.ACCOUNT_OPENING);
      paymentRules.canExecuteOpeningTransaction(account, transaction, balance);

      Transaction openingTransaction =
          transactionMapper.mapTransaction(entityId, transactionId, transactionRequest);
      openingTransaction.setEntryType(EntryType.DEBIT);
      openingTransaction.setTransactionType(TransactionType.ACCOUNT_OPENING);

      Balance openingBalance =
          transactionMapper.mapBalance(
              transactionRequest.getAccountNumber(), transactionRequest.getAmount());

      transactionRepository.save(openingTransaction);
      balanceRepository.save(openingBalance);

      return TransactionResponse.builder().transactionId(transactionId).success(true).build();
    } catch (Exception e) {
      log.error(e.getMessage());
      return TransactionResponse.builder().transactionId(transactionId).success(false).build();
    }
  }

  public TransactionResponse paymentTransaction(
      String entityId, TransactionRequest transactionRequest) {
    UUID transactionId = UUID.randomUUID();
    try {
      AccountDetailedResponse account =
          accountServiceClient.getAccount(transactionRequest.getAccountNumber());
      balance = getBalance(transactionRequest.getAccountNumber());
      BigDecimal balanceAmount = getBalance(transactionRequest.getAccountNumber()).getBalance();

      paymentRules.canPay(account, balanceAmount);

      Transaction transaction =
          transactionMapper.mapTransaction(entityId, transactionId, transactionRequest);
      transaction.setEntryType(EntryType.CREDIT);
      transaction.setTransactionType(TransactionType.PAYMENT);

      balance.setBalance(balanceAmount.subtract(transactionRequest.getAmount()));
      balance.setUpdatedAt(LocalDateTime.now());

      transactionRepository.save(transaction);
      balanceRepository.save(balance);

      return TransactionResponse.builder().transactionId(transactionId).success(true).build();
    } catch (Exception e) {
      log.error(e.getMessage());
      return TransactionResponse.builder().transactionId(transactionId).success(false).build();
    }
  }

  public TransactionsResponse getTransactions(UUID accountNumber) {
    Optional<List<Transaction>> transactions =
        transactionRepository.findByAccountNumber(accountNumber);

    if (transactions.isEmpty() || transactions.get().getFirst() == null) {
      throw new TransactionsNotFoundException("No transactions found for account");
    }
    return transactionMapper.mapTransactionResponse(
        transactions.get(), getBalance(accountNumber).getBalance());
  }

  public Balance getBalance(UUID accountNumber) {
    Optional<Balance> balance = balanceRepository.findByAccountNumber(accountNumber);

    if (balance.isEmpty()) {
      throw new BalanceNotFoundException("Balance not found for account");
    }
    return balance.get();
  }
}
