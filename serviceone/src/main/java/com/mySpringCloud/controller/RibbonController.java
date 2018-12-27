package com.mySpringCloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@RestController
public class RibbonController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/hello")
	public String hello(String name){return "hello,这是由serviceone发布的--- "+name;}

	//访问此接口调用其他注册的微服务
    @RequestMapping("/sone")
    public String hi(@RequestParam String name){
        return restTemplate.getForObject("http://servicetwo/hi?name="+name, String.class);
    }
}
