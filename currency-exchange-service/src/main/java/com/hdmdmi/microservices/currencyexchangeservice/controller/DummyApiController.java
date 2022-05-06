package com.hdmdmi.microservices.currencyexchangeservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class DummyApiController {

	Logger logger =LoggerFactory.getLogger(DummyApiController.class);
	
	@GetMapping("/dummy-api")
	// if we want to retry a method multiple times for an inconsistent api call or we can use @Retry from resilience4j
	// we can set different configuration and properties in application.properties file and the name for that specific configuration
	// will be pass in this name parameter of the @Retry
	// we can add the fall back method which can send some default values instead of sending error 
	// by default retry will happen for 3 times we can increase that in application.properties file
	// and pass that config name here instead of default 
	
	//set the fallback method i.e if the api failed after the max retry the fallbackMethod is executed 
	@Retry(name= "mycustomname" , fallbackMethod = "fallbackMethodName")
	//Circuit Breaker will stop sending the request if the api is not responding for a long time 
	//change the default values in application.properties file
	@CircuitBreaker(name = "default",fallbackMethod = "fallbackMethodName")
	
	// rate limiter will help to set rate limit for the api calls if its set to 10 request per second for the 11th request it will 
	// send response off fall back method 
	//change the default values in application.properties file
	@RateLimiter(name = "default",fallbackMethod = "fallbackMethodName")
	// bulk head will set config for the concurrent request send
	//change the default values in application.properties file
	@Bulkhead(name = "default",fallbackMethod = "fallbackMethodName")
	public String getWontWorkApiResponse() {
		logger.info("calling the api ->http://localhost/wontWorkApi");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost/wontWorkApi",String.class);
		return forEntity.getBody();
	}
	
	// The fallbackMethod should always accept a Throwable since Exception extends Throwable we can use that
	String fallbackMethodName(Exception ex) {
		// custom logic 
		logger.error("failed to call the api sending the default fallbackmessage");
		return "default fallback-message";
	}
}
