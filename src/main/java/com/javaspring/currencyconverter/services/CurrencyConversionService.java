package com.javaspring.currencyconverter.services;

import com.javaspring.currencyconverter.pojo.response.CurrencyRateResponsePojo;

public interface CurrencyConversionService {


    CurrencyRateResponsePojo getCurrencyConversionRate();
}
