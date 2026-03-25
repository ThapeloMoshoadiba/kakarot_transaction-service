package com.capsule.corp.domain.validation.rules;

import com.capsule.corp.common.exception.BusinessRuleException;
import com.capsule.corp.infrastructure.http.clients.accounts.resources.AccountDetailedResponse;
import com.capsule.corp.infrastructure.http.resources.Balance;
import com.capsule.corp.infrastructure.http.resources.Transaction;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRules {

  public void canPay(AccountDetailedResponse account, BigDecimal balance) {
    log.info("Running Payment Rules for account [{}]", account);
    isAccountOpen(account);
    isPositiveBalance(balance);

    log.info("Payment Rules Passed for account [{}]", account);
  }

  public void canExecuteOpeningTransaction(
      AccountDetailedResponse account,
      Optional<Transaction> transaction,
      Optional<Balance> balance) {
    log.info("Running Opening Transaction Rules");
    isAccountOpen(account);
    isAccountOpeningTransaction(transaction);
    isBalancePresent(balance);

    log.info("Opening Transaction Rules Passed for account [{}]", account);
  }

  private void isAccountOpen(AccountDetailedResponse account) {
    if (!account.isSuccess()) {
      throw new BusinessRuleException("Account not found");
    }
    if (account.getAccounts().getFirst() != null
        && !account.getAccounts().getFirst().getAccountStatus().equals("OPEN")) {
      throw new BusinessRuleException("Account must be OPEN to make payment");
    }
    log.info("Account is open");
  }

  private void isBalancePresent(Optional<Balance> balance) {
    if (balance.isPresent()) {
      throw new BusinessRuleException("Balance must be absent");
    }
    log.info("Balance is absent");
  }

  private void isAccountOpeningTransaction(Optional<Transaction> transaction) {
    if (transaction.isPresent()) {
      throw new BusinessRuleException("Opening Transaction already exists");
    }
    log.info("Opening Transaction is absent");
  }

  private void isPositiveBalance(BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new BusinessRuleException("Balance must be positive");
    }
    log.info("Balance is positive");
  }
}
