package com.coindesk.digitalcurrency;


import com.coindesk.digitalcurrency.data.CurrencyRateData;
import com.coindesk.digitalcurrency.service.CurrencyMainService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyMainServiceTests {

	@Autowired
	CurrencyMainService currencyMainService;

	@Test
	public void printRatesTest() {

		CurrencyRateData currencyRate = createMockData();

		currencyMainService.printRates(currencyRate);

	}

	public CurrencyRateData createMockData(){

		  CurrencyRateData currencyRateData = new CurrencyRateData();

		currencyRateData.setCurrentRate(18.000);
		currencyRateData.setMaxRate(24.000);
		currencyRateData.setMinRate(16.000);
		currencyRateData.setMinRateDate(LocalDate.now().minusDays(20).toString());
		currencyRateData.setMaxRateDate(LocalDate.now().minusDays(10).toString());
		currencyRateData.setCurrencyNotFound(true);
		currencyRateData.setCode("TRY");

		return currencyRateData;
	}

}
