package com.hdmdmi.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hdmdmi.microservices.currencyconversionservice.bean.CurrencyConversion;
import com.hdmdmi.microservices.currencyconversionservice.proxy.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionController {
	static final String uri = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";

	@Autowired
	CurrencyExchangeProxy currencyExchangeProxy;

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion retriveConverstion(@PathVariable String from, @PathVariable String to,
			@PathVariable Long quantity) {
		HashMap<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversion> currencyConversionEntity = new RestTemplate().getForEntity(uri,
				CurrencyConversion.class, uriVariables);
		CurrencyConversion currencyConversion = currencyConversionEntity.getBody();
		currencyConversion.setQuantity(quantity);
		currencyConversion.setTotalCalculatedAmount(
				currencyConversion.getConversionMultiple().multiply(new BigDecimal(quantity)));
		return currencyConversion;
	}

	@GetMapping("/currency-conversion-feing/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion retriveConverstionfeing(@PathVariable String from, @PathVariable String to,
			@PathVariable Long quantity) {
		CurrencyConversion currencyConversion = currencyExchangeProxy.retriveExchange(from, to);
		currencyConversion.setQuantity(quantity);
		currencyConversion.setTotalCalculatedAmount(
				currencyConversion.getConversionMultiple().multiply(new BigDecimal(quantity)));
		return currencyConversion;
	}

}
