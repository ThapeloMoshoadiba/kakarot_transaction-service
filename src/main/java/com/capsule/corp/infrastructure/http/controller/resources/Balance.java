package com.capsule.corp.infrastructure.http.controller.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Balance {

    UUID id;
    LocalDate createdAt;
    LocalDate updatedAt;
    String accountNumber;
    BigDecimal amount;
}
