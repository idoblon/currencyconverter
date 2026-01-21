package com.javaspring.currencyconverter.util.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${exchange.api.full.url}")
    private String apiFullUrl;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(apiFullUrl)
                .build();
    }
}
