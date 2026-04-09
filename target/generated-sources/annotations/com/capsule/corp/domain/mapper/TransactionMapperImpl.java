package com.capsule.corp.domain.mapper;

import com.capsule.corp.infrastructure.http.controller.resources.request.TransactionRequest;
import com.capsule.corp.infrastructure.http.controller.resources.response.TransactionsResponse;
import com.capsule.corp.infrastructure.http.resources.Balance;
import com.capsule.corp.infrastructure.http.resources.Transaction;
import com.capsule.corp.infrastructure.http.resources.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-09T02:50:18+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction mapTransaction(String entityId, UUID transactionId, TransactionRequest transactionRequest) {
        if ( entityId == null && transactionId == null && transactionRequest == null ) {
            return null;
        }

        Transaction.TransactionBuilder transaction = Transaction.builder();

        if ( transactionRequest != null ) {
            transaction.amount( transactionRequest.getAmount() );
            transaction.accountNumber( transactionRequest.getAccountNumber() );
        }
        transaction.initiator( entityId );
        transaction.transactionId( transactionId );
        transaction.timestamp( LocalDateTime.now() );

        return transaction.build();
    }

    @Override
    public TransactionsResponse mapTransactionResponse(List<Transaction> transactions, BigDecimal balance) {
        if ( transactions == null && balance == null ) {
            return null;
        }

        TransactionsResponse.TransactionsResponseBuilder transactionsResponse = TransactionsResponse.builder();

        List<Transaction> list = transactions;
        if ( list != null ) {
            transactionsResponse.transactions( new ArrayList<Transaction>( list ) );
        }
        transactionsResponse.balance( balance );

        return transactionsResponse.build();
    }

    @Override
    public Balance mapBalance(UUID accountNumber, BigDecimal balance) {
        if ( accountNumber == null && balance == null ) {
            return null;
        }

        Balance.BalanceBuilder balance1 = Balance.builder();

        balance1.accountNumber( accountNumber );
        balance1.balance( balance );
        balance1.id( UUID.randomUUID() );
        balance1.createdAt( LocalDateTime.now() );

        return balance1.build();
    }
}
