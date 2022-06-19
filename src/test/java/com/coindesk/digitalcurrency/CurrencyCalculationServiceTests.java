package com.coindesk.digitalcurrency;


import com.coindesk.digitalcurrency.data.CurrencyRateData;
import com.coindesk.digitalcurrency.service.CurrencyCalculationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyCalculationServiceTests {

    @Autowired
    CurrencyCalculationService currencyCalculationService;

    @Test
    public void getBTCRateByCurrencySuccessTest() throws IOException {
        String currency = "TRY";
        CurrencyRateData currencyRate = currencyCalculationService.getBTCRateByCurrency(currency);
        assertNotNull("Should return current Rate", currencyRate.getCurrentRate());
    }

    @Test
    public void getBTCRateByCurrencyFailedTest() throws IOException {
        String currency = "TL";
        CurrencyRateData currencyRate = currencyCalculationService.getBTCRateByCurrency(currency);
        assertNull("Should return null current Rate", currencyRate.getCurrentRate());
    }

    @Test(expected = HttpClientErrorException.class)
    public void calculateMinAndMaxBTCRatesFailedTest() {
        String currency = "TL";
        currencyCalculationService.calculateMinAndMaxBTCRates(currency);
    }

    @Test
    public void calculateMinAndMaxBTCRatesSuccessTest() {
        String currency = "TRY";
        CurrencyRateData currencyRate = currencyCalculationService.calculateMinAndMaxBTCRates(currency);
        assertNotNull(" return Max Rate", currencyRate.getMaxRate());
        assertNotNull(" return Min Rate", currencyRate.getMinRate());
    }

}
