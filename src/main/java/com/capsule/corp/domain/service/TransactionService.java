package com.capsule.corp.domain.service;

import com.capsule.corp.common.exception.BalanceNotFoundException;
import com.capsule.corp.common.exception.TransactionsNotFoundException;
import com.capsule.corp.domain.mapper.TransactionMapper;
import com.capsule.corp.domain.persistance.BalanceRepository;
import com.capsule.corp.domain.persistance.TransactionRepository;
import com.capsule.corp.domain.validation.rules.TransactionRules;
import com.capsule.corp.infrastructure.http.clients.accounts.AccountServiceClient;
import com.capsule.corp.infrastructure.http.clients.accounts.resources.AccountDetailedResponse;
import com.capsule.corp.infrastructure.http.clients.accounts.resources.BasicAccountRequest;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

  private final TransactionRules transactionRules;
  private final BalanceRepository balanceRepository;
  private final TransactionMapper transactionMapper;
  private final AccountServiceClient accountServiceClient;
  private final TransactionRepository transactionRepository;

  private Balance balance;
  private AccountDetailedResponse account;

  public ResponseEntity<TransactionResponse> openingTransaction(
          final String entityId, final TransactionRequest transactionRequest) {
    UUID transactionId = UUID.randomUUID();

    transactionRules.canExecuteOpeningTransaction(getAccount(transactionRequest.getAccountNumber()));

    Transaction openingTransaction = transactionMapper.mapTransaction(entityId, transactionId, transactionRequest);
    openingTransaction.setEntryType(EntryType.DEBIT);
    openingTransaction.setTransactionType(TransactionType.ACCOUNT_OPENING);

    transactionRepository.save(openingTransaction);
    balanceRepository.save(transactionMapper.mapBalance(transactionRequest.getAccountNumber(), transactionRequest.getAmount()));

    return ResponseEntity.ok(TransactionResponse.builder().transactionId(transactionId).build());
  }

  public ResponseEntity<TransactionResponse> paymentTransaction(
          final String entityId, final TransactionRequest transactionRequest) {
    UUID transactionId = UUID.randomUUID();

    account = getAccount(transactionRequest.getAccountNumber());
    balance = getBalance(transactionRequest.getAccountNumber());
    BigDecimal balanceAmount = balance.getBalance();

    transactionRules.canPay(account, balanceAmount);

    Transaction transaction = transactionMapper.mapTransaction(entityId, transactionId, transactionRequest);
    transaction.setEntryType(EntryType.CREDIT);
    transaction.setTransactionType(TransactionType.PAYMENT);

    balance.setBalance(balanceAmount.subtract(transactionRequest.getAmount()));
    balance.setUpdatedAt(LocalDateTime.now());

    transactionRepository.save(transaction);
    balanceRepository.save(balance);

    if (getBalance(transactionRequest.getAccountNumber()).getBalance().compareTo(BigDecimal.ZERO) <= 0) {
      closingTransaction(entityId, TransactionRequest.builder().accountNumber(transactionRequest.getAccountNumber()).amount(BigDecimal.ZERO).build());
    }

    return ResponseEntity.ok(TransactionResponse.builder().transactionId(transactionId).build());
  }

  public ResponseEntity<TransactionsResponse> getTransactions(final UUID accountNumber) {
    Optional<List<Transaction>> transactions = transactionRepository.findByAccountNumber(accountNumber);

    if (transactions.isEmpty() || transactions.get().getFirst() == null) {
      throw new TransactionsNotFoundException("No transactions found for account");
    }

    return ResponseEntity.ok(transactionMapper.mapTransactionResponse(transactions.get(), getBalance(accountNumber).getBalance()));
  }

  public ResponseEntity<TransactionResponse> closingTransaction(
          final String entityId, final TransactionRequest transactionRequest) {
    UUID transactionId = UUID.randomUUID();

    account = getAccount(transactionRequest.getAccountNumber());
    balance = getBalance(transactionRequest.getAccountNumber());

    transactionRules.canExecuteClosingTransaction(account, transactionRequest.getAmount());

    Transaction closingTransaction = transactionMapper.mapTransaction(entityId, transactionId, transactionRequest);
    closingTransaction.setEntryType(EntryType.CREDIT);
    closingTransaction.setTransactionType(TransactionType.ACCOUNT_CLOSING);

    balance.setBalance(balance.getBalance().subtract(transactionRequest.getAmount()));
    balance.setUpdatedAt(LocalDateTime.now());

    transactionRepository.save(closingTransaction);
    balanceRepository.save(balance);

    accountServiceClient.closeAccount(BasicAccountRequest.builder().accountNumber(transactionRequest.getAccountNumber()).reason("Debt Balance Zero").build());

    return ResponseEntity.ok(TransactionResponse.builder().transactionId(transactionId).build());
  }

  private Balance getBalance(final UUID accountNumber) {
    Optional<Balance> optionalBalance = balanceRepository.findByAccountNumber(accountNumber);

    if (optionalBalance.isEmpty()) {
      throw new BalanceNotFoundException("Balance not found");
    }
    return optionalBalance.get();
  }

  private AccountDetailedResponse getAccount(final UUID accountNumber) {
    return accountServiceClient.getAccount(accountNumber);
  }
}
