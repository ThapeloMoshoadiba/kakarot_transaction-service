package com.capsule.corp.infrastructure.http.controller.resources.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    UUID transactionId;
    boolean success;
}
