package com.hdmdmi.microservices.currencyconversionservice.bean;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrencyConversion {
	private Long id;
	private String from;
	private String to;
	private BigDecimal conversionMultiple;
	private Long quantity;
	private BigDecimal totalCalculatedAmount;
	private String environment;
	
	public CurrencyConversion(Long id, String from, String to, BigDecimal conversionMultiple, Long quantity,
			BigDecimal totalCalculatedAmount) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.conversionMultiple = conversionMultiple;
		this.quantity = quantity;
		this.totalCalculatedAmount = totalCalculatedAmount;
	}
	
	
	
}
