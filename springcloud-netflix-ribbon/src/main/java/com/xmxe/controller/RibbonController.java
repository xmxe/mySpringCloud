package com.xmxe.controller;

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

	//访问此接口调用其他注册的微服务
    @RequestMapping("/ribbon")
    @ResponseBody
    public String hi(@RequestParam String name){
        //ribbon不支持下划线
        String url = "http://providerService/hello_ribbon?name="+name;
        return restTemplate.getForObject(url, String.class);
    }
}
