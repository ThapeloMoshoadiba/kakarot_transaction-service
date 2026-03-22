package com.capsule.corp.infrastructure.http.controller.resources.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public class PaymentResponse {

    UUID paymentId;
    String accountNumber;
    BigDecimal amount;
    boolean success;
}
