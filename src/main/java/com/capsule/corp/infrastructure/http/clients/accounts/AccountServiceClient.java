package com.capsule.corp.infrastructure.http.clients.accounts;

import com.capsule.corp.common.config.AppConfiguration;
import com.capsule.corp.infrastructure.http.clients.accounts.resources.AccountDetailedResponse;
import com.capsule.corp.infrastructure.http.clients.accounts.resources.BasicAccountRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceClient {

  private final AppConfiguration.AccountServiceConfig config;
  private final RestClient accountServiceRestClient;

  public AccountDetailedResponse getAccount(final UUID accountNumber) {
    ResponseEntity<AccountDetailedResponse> response =
        accountServiceRestClient
            .method(HttpMethod.GET)
            .uri(
                UriComponentsBuilder.fromHttpUrl(config.getBaseUrl())
                    .queryParam("accountNumber", accountNumber)
                    .toUriString())
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(new ParameterizedTypeReference<>() {});

    return response.getBody();
  }

  public void closeAccount(final BasicAccountRequest accountRequest) {
    accountServiceRestClient
        .method(HttpMethod.PUT)
        .uri("%s%s".formatted(config.getBaseUrl(), config.getCloseEndpoint()))
        .body(accountRequest)
        .contentType(MediaType.APPLICATION_JSON)
        .retrieve()
        .toEntity(new ParameterizedTypeReference<>() {});
  }
}
