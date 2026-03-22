package com.capsule.corp.domain.validation.rules;

import com.capsule.corp.common.exception.BusinessRuleException;
import com.capsule.corp.infrastructure.http.clients.accounts.resources.AccountDetailedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRules {

    public void canPay(AccountDetailedResponse accountNumber, BigDecimal balance) {
        log.info("Running Payment Rules for account [{}]", accountNumber);
        isAccountOpen(accountNumber);
        isPositiveBalance(balance);

        log.info("Payment Rules Passed for account [{}]", accountNumber);
    }

    private void isAccountOpen(AccountDetailedResponse account) {
        if (!account.isSuccess()) {
            throw new BusinessRuleException("Account not found");
        }
        if (account.getAccount() != null && !account.getAccount().getAccountStatus().equals("OPEN")) {
            throw new BusinessRuleException("Account must be OPEN to make payment");
        }
    }

    private void isPositiveBalance(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new BusinessRuleException("Balance must be positive");
        }
    }

}
