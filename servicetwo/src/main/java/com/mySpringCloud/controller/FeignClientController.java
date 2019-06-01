package com.mySpringCloud.controller;

import com.mySpringCloud.service.FeignClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope//在客户端执行/refresh的时候就会更新此类下面的变量值
@RestController
public class FeignClientController {
    @Value("${jdbcUrl}")
    String jdbcUrl;

    @Autowired
    FeignClientService feignClient;

    @RequestMapping("/hi")
    public String hi(String name){return "hi,这是由servicetwo提供的，会由ribbon调用-- "+name;}

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
