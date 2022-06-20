package com.coindesk.digitalcurrency;


import com.coindesk.digitalcurrency.service.CurrencyValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyValidationServiceTest{

    @Value("${validCurrencysUrl}")
    String VALID_CURRENCY_URL;

    @Autowired
    CurrencyValidationService currencyValidationService;


   @Test
    public void validateFailTest() throws IOException {

      String currencyCode = "TL";
      boolean isValid = currencyValidationService.validate(currencyCode, VALID_CURRENCY_URL);
      assertNotEquals("Should not equals, because it's not valid currency", true, isValid);

   }


   @Test
   public void validateSuccessTest() throws IOException {

      String currencyCode = "TRY";
       boolean isValid = currencyValidationService.validate(currencyCode, VALID_CURRENCY_URL);
       assertEquals("Should equals, because it's valid currency", true, isValid);
   }


}
