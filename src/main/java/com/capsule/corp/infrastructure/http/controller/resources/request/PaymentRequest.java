package com.capsule.corp.infrastructure.http.controller.resources.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    UUID paymentId;
    String accountNumber;
    BigDecimal amount;
}
