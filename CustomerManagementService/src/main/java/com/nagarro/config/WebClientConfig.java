package com.nagarro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

//WebClient Config file for customer management Service
@Configuration
public class WebClientConfig {
	
	@Bean
	public WebClient webClient() {
		return WebClient.builder().build();
	}
}
