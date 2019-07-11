package com.feign.controller;

import com.feign.service.FeignClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RefreshScope//在客户端执行/refresh的时候就会更新此类下面的变量值
@Controller
public class FeignClientController {
    //@Value("${jdbcUrl}")
    String jdbcUrl;

    @Autowired
    FeignClientService feignClient;

    @RequestMapping("/hi")
    @ResponseBody
    public String hi(String name){return "hi,这是由servicetwo提供的，会由ribbon调用-- "+name;}

    //调用其他注册的微服务
    @GetMapping(value = "sresis")
    @ResponseBody
    public String sredis(){
        return feignClient.session_redis();
    }

    //调用其他注册的微服务
    @GetMapping(value = "stwo")
    @ResponseBody
    public String ttt(String name){
        return feignClient.serviceOne(name);
    }

    //从spring cloud config(git)上获取得到的


    @RequestMapping("/getJdbcUrl")
    @ResponseBody
    public String getJdbcUrl() {
        return "jdbcUrl:" + jdbcUrl;
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
