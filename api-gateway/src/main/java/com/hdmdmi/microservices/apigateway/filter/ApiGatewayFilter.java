package com.hdmdmi.microservices.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;


@Component
public class ApiGatewayFilter implements GlobalFilter {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("request made to the url -> {}",exchange.getRequest().getURI());
		return chain.filter(exchange);
	}

}
