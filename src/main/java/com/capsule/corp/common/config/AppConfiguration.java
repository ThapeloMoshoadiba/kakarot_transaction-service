package com.capsule.corp.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfiguration {

    @Data
    @Component
    @ConfigurationProperties(prefix = "app.integration.account-service")
    public class AccountServiceConfig extends IntegrationConfig {
        //private String someEndpoint;
    }
}
