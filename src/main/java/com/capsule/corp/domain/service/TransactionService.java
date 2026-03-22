package com.capsule.corp.domain.service;

import com.capsule.corp.common.exception.BalanceNotFoundException;
import com.capsule.corp.common.exception.TransactionsNotFoundException;
import com.capsule.corp.domain.mapper.TransactionMapper;
import com.capsule.corp.domain.persistance.BalanceRepository;
import com.capsule.corp.domain.persistance.TransactionRepository;
import com.capsule.corp.domain.validation.rules.PaymentRules;
import com.capsule.corp.infrastructure.http.clients.accounts.AccountServiceClient;
import com.capsule.corp.infrastructure.http.clients.accounts.resources.AccountDetailedResponse;
import com.capsule.corp.infrastructure.http.resources.Balance;
import com.capsule.corp.infrastructure.http.controller.resources.request.TransactionRequest;
import com.capsule.corp.infrastructure.http.controller.resources.response.TransactionResponse;
import com.capsule.corp.infrastructure.http.resources.enums.EntryType;
import com.capsule.corp.infrastructure.http.resources.Transaction;
import com.capsule.corp.infrastructure.http.controller.resources.response.TransactionsResponse;
import com.capsule.corp.infrastructure.http.resources.enums.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountServiceClient accountServiceClient;
    private final TransactionRepository transactionRepository;
    private final BalanceRepository balanceRepository;
    private final TransactionMapper transactionMapper;
    private final PaymentRules paymentRules;

    public TransactionResponse openingTransaction(String entityId, TransactionRequest transactionRequest) {
        try {
            Transaction transaction = transactionMapper.mapTransaction(entityId, transactionRequest);
            transaction.setEntryType(EntryType.DEBIT);
            transaction.setTransactionType(TransactionType.ACCOUNT_OPENING);

            Balance balance = transactionMapper.mapBalance(transactionRequest.getAccountNumber(), transactionRequest.getAmount());
            balance.setCreatedAt(LocalDate.now());

            transactionRepository.save(transaction);
            balanceRepository.save(balance);

            return transactionMapper.mapTransactionResponse(transactionRequest, true);
        } catch (Exception e) {
            log.error(e.getMessage());
            return transactionMapper.mapTransactionResponse(transactionRequest, false);
        }
    }

    public TransactionResponse paymentTransaction(String entityId, TransactionRequest transactionRequest) {
        try {
            AccountDetailedResponse account = accountServiceClient.getAccount(transactionRequest.getAccountNumber());
            BigDecimal balanceAmount = getBalance(transactionRequest.getAccountNumber()).getAmount();

            paymentRules.canPay(account, balanceAmount);

            Transaction transaction = transactionMapper.mapTransaction(entityId, transactionRequest);
            transaction.setEntryType(EntryType.CREDIT);
            transaction.setTransactionType(TransactionType.PAYMENT);

            Balance balance = transactionMapper.mapBalance(transactionRequest.getAccountNumber(), balanceAmount.subtract(transactionRequest.getAmount()));
            balance.setUpdatedAt(LocalDate.now());

            transactionRepository.save(transaction);
            balanceRepository.save(balance);

            return transactionMapper.mapTransactionResponse(transactionRequest, true);
        } catch (Exception e) {
            log.error(e.getMessage());
            return transactionMapper.mapTransactionResponse(transactionRequest, false);
        }
    }

    public TransactionsResponse getTransactions(String accountNumber) {

            BigDecimal balanceAmount = getBalance(accountNumber).getAmount();
            Optional<List<Transaction>> transactions = transactionRepository.findByAccountNumber(accountNumber);

            if (transactions.isEmpty() || transactions.get().getFirst() == null) {
                throw new TransactionsNotFoundException("No transactions found for account");
            }

            return transactionMapper.mapTransactionResponse(transactions.get(), balanceAmount);

    }

    public Balance getBalance(String accountNumber) {
        Optional<Balance> balance = balanceRepository.findByAccountNumber(accountNumber);

        if(balance.isEmpty()){
            throw new BalanceNotFoundException("Balance not found for account");
        }

        return balance.get();
    }

}
