package com.javaspring.currencyconverter.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class CurrencyConversionApiCaller {

    private final RestClient restClient;

    public String fetchExchangeRate() {
        return restClient.get()
                .retrieve()
                .body(String.class);
    }
}
