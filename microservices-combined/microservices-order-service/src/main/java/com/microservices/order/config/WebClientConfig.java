package com.microservices.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig 
{
	@Bean
	@LoadBalanced //Add the client side load balancing. If not added then will be confused to call which instance
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}
	
}
