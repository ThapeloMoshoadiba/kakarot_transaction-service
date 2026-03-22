package com.capsule.corp.infrastructure.http.clients.accounts;

import com.capsule.corp.infrastructure.http.clients.accounts.resources.AccountDetailedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import com.capsule.corp.common.config.AppConfiguration;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceClient {

    private final AppConfiguration.AccountServiceConfig config;
    private final RestClient accountsRestClient;

    public AccountDetailedResponse getAccount(final String accountNumber) {
        try {
            ResponseEntity<AccountDetailedResponse> response = accountsRestClient
                    .method(HttpMethod.GET)
                    .uri(UriComponentsBuilder.fromHttpUrl(config.getBaseUrl())
                            .queryParam("accountNumber", accountNumber)
                            .toUriString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<>() {});

            return response.getBody();
        } catch (Exception e) {
            log.error("Error Getting Account: [{}] [{}]", accountNumber, e.getMessage());
        }
        return null;
    }
}
