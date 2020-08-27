package com.xmxe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class NacosServiceProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosServiceProviderApplication.class,args);
	}

}

