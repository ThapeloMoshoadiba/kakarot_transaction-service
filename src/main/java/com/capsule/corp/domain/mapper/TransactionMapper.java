package com.capsule.corp.domain.mapper;

import com.capsule.corp.infrastructure.http.controller.resources.Balance;
import com.capsule.corp.infrastructure.http.controller.resources.request.PaymentRequest;
import com.capsule.corp.infrastructure.http.controller.resources.Transaction;
import com.capsule.corp.infrastructure.http.controller.resources.response.TransactionsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        imports = {UUID.class, LocalDate.class, LocalDate.class})
public interface TransactionMapper {

    @Mapping(target = "initiator", source = "entityId")
    @Mapping(target = "amount", source = "paymentRequest.amount")
    @Mapping(target = "timeStamp", expression = "java(LocalDate.now())")
    @Mapping(target = "accountNumber", source = "paymentRequest.accountNumber")
    @Mapping(target = "transactionId", source = "paymentRequest.paymentId")
    Transaction mapTransactionRequest(String entityId, PaymentRequest paymentRequest);


    @Mapping(target = "updatedAt", expression = "java(LocalDate.now())")
    @Mapping(target = "accountNumber", source = "accountNumber")
    @Mapping(target = "amount", source = "balance")
    Balance mapUpdateBalance(String accountNumber, BigDecimal balance);

    TransactionsResponse mapTransactionResponse(List<Transaction> transactions, BigDecimal balance);

}
