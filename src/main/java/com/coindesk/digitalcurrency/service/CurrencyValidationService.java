package com.coindesk.digitalcurrency.service;


import com.coindesk.digitalcurrency.data.CurrencyValidData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyValidationService {

    private final RestTemplate restTemplate;

    public boolean validate(String currency, String url)  {

         List<CurrencyValidData> supportedCurrencyList = new ArrayList<>();
        
        if (CollectionUtils.isEmpty(supportedCurrencyList)) {
            ResponseEntity<List<CurrencyValidData>> currencyResponse =
                    restTemplate
                            .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CurrencyValidData>>() {
            });

            supportedCurrencyList = currencyResponse.getBody();
        }

        return supportedCurrencyList
                .stream()
                .anyMatch(x -> x.getCurrency().equals(currency));

    }
}