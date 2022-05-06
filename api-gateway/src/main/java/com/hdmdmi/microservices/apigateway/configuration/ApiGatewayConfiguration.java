package com.hdmdmi.microservices.apigateway.configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	// When a function returns the object then mark it as @Bean so that spring will
	// add that to context path
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		// route method takes a function so we are creating the lambda function
		return builder.routes()
				// each uri has one route function with it this one is for /get endpoint
				.route(// f is passed as the input predicate if f.path("/get") is present it is mapped
				// to uri ("http://httpbin.org:80")
				f -> f.path("/get")
						// filters are used to add addtional information with the request if you
						// microservices required any auth token you can add this
						// here as and header and the redirect to the corresponding uri
						.filters(filter -> filter.addRequestHeader("Auth", "42942jerkjda734na8d"))
						.uri("http://httpbin.org:80"))
				// this one is for /currency-exchange endpoint
				// /** is the regex pattern
				//.route(r -> r.path("/currency-exchange")
				//.uri("http://httpbin.org:80"))
				.route(p -> p.path("/currency-exchange/**")
						.uri("lb://currency-exchange"))
				.route(p -> p.path("/currency-conversion/**")
						.uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion-feing/**")
						.uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion-new/**")
						.filters(f -> f.rewritePath(
								"/currency-conversion-new/(?<segment>.*)", 
								"/currency-conversion-feing/${segment}"))
						.uri("lb://currency-conversion"))
				.build();
	}

}








