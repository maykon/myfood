package com.myfood.infra.integrations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class IntegrationConfigurations {
    @Value("${api.integrations.payment.url}")
    String paymentUrl;

    @Bean
    public WebClient paymentApiClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(paymentUrl).build();
    }
}
