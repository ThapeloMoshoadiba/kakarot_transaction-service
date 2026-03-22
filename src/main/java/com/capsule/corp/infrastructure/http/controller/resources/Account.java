package com.capsule.corp.infrastructure.http.controller.resources;

import com.capsule.corp.infrastructure.http.controller.resources.enums.AccountStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    UUID accountId;
    LocalDate createdAt;
    String cifNumber;
    UUID accountNumber;
    AccountStatus accountStatus;
    BigDecimal initialCreditAmount;
    LocalDate closedAt;
    String reasonForClose;
}
