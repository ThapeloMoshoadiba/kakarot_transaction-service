package com.capsule.corp.domain.validation.rules;

import com.capsule.corp.common.exception.BalanceNotFoundException;
import com.capsule.corp.common.exception.BusinessRuleException;
import com.capsule.corp.domain.persistance.BalanceRepository;
import com.capsule.corp.domain.persistance.TransactionRepository;
import com.capsule.corp.infrastructure.http.clients.accounts.resources.AccountDetailedResponse;
import com.capsule.corp.infrastructure.http.resources.Balance;
import com.capsule.corp.infrastructure.http.resources.Transaction;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import com.capsule.corp.infrastructure.http.resources.enums.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionRules {

  private final BalanceRepository balanceRepository;
  private final TransactionRepository transactionRepository;

  public void canPay(final AccountDetailedResponse account, final BigDecimal balance) {
    log.info("Running Payment Rules for account [{}]", account);
    isAccountOpen(account);
    isPositiveBalance(balance);

    log.info("Payment Rules Passed for account [{}]", account);
  }

  public void canExecuteOpeningTransaction(final AccountDetailedResponse account) {
    log.info("Running Opening Transaction Rules");
    UUID accountNumber = account.getAccounts().getFirst().getAccountNumber();

    isAccountOpen(account);
    isAccountOpeningTransaction(accountNumber);
    // there also should not be any transactions at all for this account
    isBalancePresent(accountNumber);

    log.info("Opening Transaction Rules Passed for account [{}]", account);
  }

  public void canExecuteClosingTransaction(final AccountDetailedResponse account, final BigDecimal paymentAmount) {
    log.info("Running Closing Transaction Rules");
    UUID accountNumber = account.getAccounts().getFirst().getAccountNumber();
    Optional<Balance> balanceEntity = getBalance(accountNumber);

    if (balanceEntity.isEmpty()){
      throw new BalanceNotFoundException("Balance not found");
    }

    isAccountOpen(account);
    isPaymentAmountValid(paymentAmount, balanceEntity.get().getBalance());
    isAccountClosingTransaction(accountNumber);

    log.info("Closing Transaction Rules Passed for account [{}]", account);
  }

  private void isAccountOpen(final AccountDetailedResponse account) {
    if (account.getAccounts().getFirst() != null
        && !account.getAccounts().getFirst().getAccountStatus().equals("OPEN")) {
      throw new BusinessRuleException("Account must be OPEN");
    }
    log.info("Account is open");
  }

  private void isBalancePresent(final UUID accountNumber) {
    if (getBalance(accountNumber).isPresent()) {
      throw new BusinessRuleException("Balance must be absent");
    }
    log.info("Balance is absent");
  }

  private void isPaymentAmountValid(final BigDecimal paymentAmount, final BigDecimal balance) {
    if (paymentAmount.compareTo(BigDecimal.ZERO) == 0
        && balance.compareTo(BigDecimal.ZERO) > 0) {
      throw new BusinessRuleException("Balance must be Zero to Close Account");
    }
    if (paymentAmount.compareTo(balance) < 0) {
      throw new BusinessRuleException(
          "Payment Amount must match or exceed balance to Close Account");
    }
    log.info("Balance Rules Assessed");
  }

  private void isAccountOpeningTransaction(final UUID accountNumber) {
    if (getTransaction(accountNumber, TransactionType.ACCOUNT_OPENING).isPresent()) {
      throw new BusinessRuleException("Opening Transaction already exists");
    }
    log.info("Opening Transaction is absent");
  }

  private void isAccountClosingTransaction(final UUID accountNumber) {
    if (getTransaction(accountNumber, TransactionType.ACCOUNT_CLOSING).isPresent()) {
      throw new BusinessRuleException("Closing Transaction already exists");
    }
    log.info("Closing Transaction is absent");
  }

  private void isPositiveBalance(final BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new BusinessRuleException("Balance must be positive");
    }
    log.info("Balance is positive");
  }

  private Optional<Transaction> getTransaction(final UUID accountNumber, final TransactionType transactionType) {
    return transactionRepository.findByAccountNumberAndTransactionType(accountNumber, transactionType);
  }

  private Optional<Balance> getBalance(final UUID accountNumber) {
    return balanceRepository.findByAccountNumber(accountNumber);
  }
}
