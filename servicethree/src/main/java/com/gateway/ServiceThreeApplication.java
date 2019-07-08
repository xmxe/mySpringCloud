package com.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication
public class ServiceThreeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceThreeApplication.class, args);
	}

}

