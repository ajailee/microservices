package com.hdmdmi.microservices.limitsservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hdmdmi.microservices.limitsservices.bean.Limits;
import com.hdmdmi.microservices.limitsservices.configuration.Configuration;

@RestController
public class LimitsController {
	@Autowired
	Configuration configuration;

	
	@GetMapping("/limits")
	public Limits retriveLimits() {
		Limits limits = new Limits(configuration.getMinimum(),configuration.getMaximum());
		return limits;
	}
}
