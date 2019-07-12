package com.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@EnableDiscoveryClient //适用于其他的注册中心，场景较丰富
@EnableEurekaClient//适用于eureka作为注册中心 场景较单一
@SpringBootApplication
public class ServiceFourApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceFourApplication.class, args);
	}

}

