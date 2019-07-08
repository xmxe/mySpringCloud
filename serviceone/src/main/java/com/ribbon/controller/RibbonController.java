package com.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
@Controller
public class RibbonController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/hello")
    @ResponseBody
	public String hello(String name){return "hello,这是由serviceone发布的,会由feign调用--- "+name;}

	//访问此接口调用其他注册的微服务
    @RequestMapping("/sone")
    @ResponseBody
    public String hi(@RequestParam String name){
        return restTemplate.getForObject("http://servicetwo/hi?name="+name, String.class);
    }
    @RequestMapping("/index")
    public String hello() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
