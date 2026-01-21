package com.javaspring.currencyconverter.services;

import com.javaspring.currencyconverter.pojo.response.CurrencyRateResponsePojo;
import com.javaspring.currencyconverter.util.CurrencyConversionApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private final CurrencyConversionApiCaller currencyConversionApiCaller;
    private final ObjectMapper objectMapper;

    @Override
    public CurrencyRateResponsePojo getCurrencyConversionRate() {
        String rawData = currencyConversionApiCaller.fetchExchangeRate();
        return objectMapper.readValue(rawData, CurrencyRateResponsePojo.class);
    }
}
