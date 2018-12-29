package com.mySpringCloud.controller;

import com.mySpringCloud.service.FeignClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignClientController {
    @Value("${jdbcUrl}")
    String jdbcUrl;

    @Autowired
    FeignClientService feignClient;

    @RequestMapping("/hi")
    public String hi(String name){return "hi,这是由servicetwo提供的-- "+name;}

    //调用其他注册的微服务
    @GetMapping(value = "stwo")
    public String ttt(String name){
        return feignClient.serviceOne(name);
    }

    //从spring cloud config(git)上获取得到的


    @RequestMapping("/getJdbcUrl")
    public String getJdbcUrl() {
        return "jdbcUrl:" + jdbcUrl;
    }

}
