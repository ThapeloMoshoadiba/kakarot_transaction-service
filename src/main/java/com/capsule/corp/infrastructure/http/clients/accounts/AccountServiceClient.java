package com.capsule.corp.infrastructure.http.clients.accounts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceClient {

    private boolean getAccount(String accountNumber) {
        return true;
    }
}
