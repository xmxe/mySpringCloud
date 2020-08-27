package com.xmxe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class NacosServiceConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosServiceConsumerApplication.class,args);
	}

	@Bean
	@LoadBalanced //开启负载均衡
	RestTemplate restTemplate(){
		return new RestTemplate();
	}
}

