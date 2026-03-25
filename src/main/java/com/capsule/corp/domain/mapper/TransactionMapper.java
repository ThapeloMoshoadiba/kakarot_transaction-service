package com.capsule.corp.domain.mapper;

import com.capsule.corp.infrastructure.http.controller.resources.request.TransactionRequest;
import com.capsule.corp.infrastructure.http.controller.resources.response.TransactionsResponse;
import com.capsule.corp.infrastructure.http.resources.Balance;
import com.capsule.corp.infrastructure.http.resources.Transaction;
import com.capsule.corp.infrastructure.http.resources.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    imports = {UUID.class, LocalDateTime.class, TransactionType.class})
public interface TransactionMapper {

  @Mapping(target = "initiator", source = "entityId")
  @Mapping(target = "amount", source = "transactionRequest.amount")
  @Mapping(target = "timestamp", expression = "java(LocalDateTime.now())")
  @Mapping(target = "transactionId", source = "transactionId")
  @Mapping(target = "accountNumber", source = "transactionRequest.accountNumber")
  Transaction mapTransaction(
      String entityId, UUID transactionId, TransactionRequest transactionRequest);

  TransactionsResponse mapTransactionResponse(List<Transaction> transactions, BigDecimal balance);

  @Mapping(target = "id", expression = "java(UUID.randomUUID())")
  @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
  @Mapping(target = "accountNumber", source = "accountNumber")
  @Mapping(target = "balance", source = "balance")
  Balance mapBalance(UUID accountNumber, BigDecimal balance);
}
