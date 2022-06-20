package com.coindesk.digitalcurrency.service;


import com.coindesk.digitalcurrency.data.CurrencyRateData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class CurrencyMainService {

	private final CurrencyCalculationService coinDeskCurrencyService;
	private final RestTemplate restTemplate;

	private CurrencyRateData currencyRate;


	public void currencyCodeInput() {

		String repeat = "y";
	    	do {
				@SuppressWarnings("resource")
				Scanner scanner = new Scanner(System.in);
				System.out.print("Please enter the currency code e.g USD, EUR, GBP, etc. : ");
				String currency = scanner.next();

				try
				{
					currencyRate = coinDeskCurrencyService.getBTCRateByCurrency(currency.toUpperCase());
				}
				catch (Exception e)
				{
					log.error("IOException occurs: "+e);
				}

				if (!currencyRate.isCurrencyNotFound())
				{
					printRates(currencyRate);
					repeat = scanner.next();
				}

		}while(repeat.equalsIgnoreCase("y"));

	}
	
	public void printRates(CurrencyRateData currencyRate) {

			System.out.println(  currencyRate.getCode() + " Current rate : "  + currencyRate.getCurrentRate());
			System.out.println("Min Bitcoin price in the last 30 days is : "+ currencyRate.getMinRateDate() +" "+ currencyRate.getCode()  + " " + currencyRate.getMinRate());
			System.out.println("Max Bitcoin price in the last 30 days is : "+ currencyRate.getMaxRateDate() +" "+ currencyRate.getCode()  + " " + currencyRate.getMaxRate());

			System.out.print("Do you want to continue? type Y or N : ");

	}
}