package com.xmxe.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsulProviderController {

	@GetMapping("hello_consul")
	public String hello_consul(String name){
		return "hello consul-->"+name;
	}
}
