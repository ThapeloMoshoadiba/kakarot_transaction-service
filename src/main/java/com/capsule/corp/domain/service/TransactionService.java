package com.capsule.corp.domain.service;

import com.capsule.corp.domain.mapper.TransactionMapper;
import com.capsule.corp.domain.persistance.BalanceRepository;
import com.capsule.corp.domain.persistance.TransactionRepository;
import com.capsule.corp.infrastructure.http.clients.accounts.AccountServiceClient;
import com.capsule.corp.infrastructure.http.controller.resources.Account;
import com.capsule.corp.infrastructure.http.controller.resources.Balance;
import com.capsule.corp.infrastructure.http.controller.resources.request.PaymentRequest;
import com.capsule.corp.infrastructure.http.controller.resources.response.PaymentResponse;
import com.capsule.corp.infrastructure.http.controller.resources.Transaction;
import com.capsule.corp.infrastructure.http.controller.resources.response.TransactionsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    public PaymentResponse payment(String entityId, PaymentRequest paymentRequest) {
        /*
            1. Get account via accountServiceClient.getAccount(paymentRequest.getAccountNumber())
            2. Get balance via getBalance(paymentRequest.getAccountNumber()).getAmount()

        */

        // 1. Account account = accountServiceClient.getAccount(paymentRequest.getAccountNumber());

        // 2.
        BigDecimal balanceAmount = getBalance(paymentRequest.getAccountNumber()).getAmount();
        Balance balance;

        // if (account.getStatus() is "OPEN" && !(balanceAmount.compareTo(BigDecimal.ZERO) <= 0)){ execute the below };

        // 3.
        transactionRepository.save(transactionMapper.mapTransactionRequest(entityId, paymentRequest));

        if (!(balanceAmount.compareTo(BigDecimal.ZERO) <= 0)) {
            // 4.
            BigDecimal newBalance = balanceAmount.subtract(paymentRequest.getAmount());
            balanceRepository.save(transactionMapper.mapUpdateBalance(paymentRequest.getAccountNumber(), newBalance));
        } else {
            // throw some error because balance is either 0 or negative OR account is not OPEN
        }

        return PaymentResponse.builder().build();
    }

    public TransactionsResponse getTransactions(String accountNumber) {
        BigDecimal balanceAmount = getBalance(accountNumber).getAmount();
    /*

        1. get all transactions
        2. get latest balance

    */

        TransactionsResponse transactionsResponse;
        List<Transaction> transactions =  new ArrayList<>();
        Optional<Balance> balance = balanceRepository.findByAccountNumber(accountNumber);
        if (balance.isPresent()) {
            balanceAmount = balance.get().getAmount();
        }

        transactionRepository.findByAccountNumber(accountNumber); // do a loop to get all and add to a transaction list

        return transactionMapper.mapTransactionResponse(transactions, balanceAmount);
    }

    public Balance getBalance(String accountNumber) {
        Optional<Balance> balance = balanceRepository.findByAccountNumber(accountNumber);
        if (balance.isPresent()) {
            return balance.get();
        }
        // else throw some balance exception
        return Balance.builder().build();
    }

    public Account getAccount(String accountNumber) {
        // accountServiceClient.getAccount(paymentRequest.getAccountNumber());
        return Account.builder().build();
    }

//    public Client getClient(String accountNumber) {
//        // accountServiceClient.getAccount(paymentRequest.getAccountNumber());
//        return Account.builder().build();
//    }

}
