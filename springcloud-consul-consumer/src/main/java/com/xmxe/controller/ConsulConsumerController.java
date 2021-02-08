package com.xmxe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
public class ConsulConsumerController {

	@Value("${student.name}")
	private String value;

	@Autowired
	private LoadBalancerClient loadBalancer;

	@Autowired
	private DiscoveryClient discoveryClient;

	/**
	 * 获取所有服务
	 */
	@RequestMapping("/services")
	public Object services() {
		log.info("consul.config value->{}",value);
		return discoveryClient.getInstances("consul-provider-service");
	}

	/**
	 * 从所有服务中选择一个服务（轮询）
	 */
	@RequestMapping("/discover")
	public Object discover() {
		List<String> servicesList = discoveryClient.getServices();
		List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances("consul-provider-service");

		return loadBalancer.choose("consul-provider-service").getUri().toString();
	}

	@RequestMapping("/call")
	public String call() {
		ServiceInstance serviceInstance = loadBalancer.choose("consul-provider-service");
		log.info("服务地址：{}", serviceInstance.getUri());
		log.info("服务名称：{}", serviceInstance.getServiceId());

		String callServiceResult = new RestTemplate().getForObject(serviceInstance.getUri().toString() + "/hello_consul?name=this is parame", String.class);
		log.info("返回结果->{}",callServiceResult);
		return callServiceResult;
	}

}

