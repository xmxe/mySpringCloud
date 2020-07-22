package com.xmxe.controller;

import com.xmxe.service.FeignClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FeignClientController {
    @Autowired
    FeignClientService feignClient;


    //调用其他注册的微服务
    @GetMapping(value = "feign")
    @ResponseBody
    public String feign(String name){
        return feignClient.hello_feign(name);
    }

}
