package com.coindesk.digitalcurrency.service;


import com.coindesk.digitalcurrency.data.CurrencyRateData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyCalculationService {

    private CurrencyRateData currencyRate;
    private final RestTemplate restTemplate;
    private final CurrencyValidationService currencyValidationService;

    @Value("${currentRateUrl}")
    private String CURRENT_RATE_URL;

    @Value("${historicalRateUrl}")
    private String HISTORICAL_RATE_URL;

    @Value("${validCurrencysUrl}")
    private String VALID_CURRENCY_URL;


    public CurrencyRateData getBTCRateByCurrency(String currency) throws IOException {
        log.info( currency + " is input ");
        currencyRate = new CurrencyRateData();
        if (currencyValidationService.validate(currency, VALID_CURRENCY_URL))
        {
            ResponseEntity<String> response = restTemplate.getForEntity(CURRENT_RATE_URL + currency + ".json", String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;
            try {
                root = mapper.readTree(response.getBody());
                JsonNode bpi = root.path("bpi");
                JsonNode requiredCurrency = bpi.get(currency);
                currencyRate = mapper.convertValue(requiredCurrency, CurrencyRateData.class);

                if (currencyRate != null)
                {
                    currencyRate = calculateMinAndMaxBTCRates(currency);
                }
            } catch (IOException e)
            {
                log.error("IOException occurs: "+e);
            }
        }
        else
        {
            currencyRate.setCurrencyNotFound(true);
            System.out.print( currency + " is not a currency. please try again: \n");
        }

        return currencyRate;
    }


    public CurrencyRateData calculateMinAndMaxBTCRates(String currency) {

        String endDate = LocalDate.now().toString();
        String startDate =  LocalDate.now().minusDays(30).toString();

        currencyRate.setStartDate(startDate);
        currencyRate.setEndDate(endDate);

        String url = HISTORICAL_RATE_URL + currency + "&start=" + startDate + "&end=" + endDate;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;

        try {
            root = mapper.readTree(response.getBody());
            JsonNode bpi = root.path("bpi");

            Map<String, Double> result = mapper.convertValue(bpi, Map.class);

            if (result != null)
            {
                Double minRate = result.entrySet()
                        .stream()
                        .min(Map.Entry.comparingByValue()).get().getValue();

                String minRateDate = result.entrySet().stream()
                        .min(Map.Entry.comparingByValue()).get().getKey();

                currencyRate.setMinRate(minRate);
                currencyRate.setMinRateDate(minRateDate);

                Double maxRate = result.entrySet().stream()
                        .max(Map.Entry.comparingByValue()).get().getValue();
                String maxRateDate = result.entrySet().stream()
                        .max(Map.Entry.comparingByValue()).get().getKey();

                currencyRate.setMaxRate(maxRate);
                currencyRate.setMaxRateDate(maxRateDate);
                currencyRate.setCurrencyNotFound(false);
            }
        }
        catch (IOException e)
        {
            log.error("Exception occurs: " + e);
        }
        return currencyRate;
    }

}
