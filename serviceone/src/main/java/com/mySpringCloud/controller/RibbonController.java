package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@RestController
public class RibbonController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/sone")
    public String hi(@RequestParam String name){
        return restTemplate.getForObject("http://servicetwo/hi?name="+name, String.class);
    }

    @RequestMapping("/hello")
	public String hi(String name){return "hello,这是serviceone调用的--- "+name;}
}
