package com.gateway.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@DefaultProperties(defaultFallback = "defaultFallback")
public class GateWayController {

    @RequestMapping("/hello")
    @ResponseBody
	public String hello(String name){return "负载均衡,hello,这是由serviceone发布的，将由feign调用--- "+name;}

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping("/hystrix")
    @ResponseBody
    public String hystrix(String name){
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "--- "+name;
    }

    @HystrixCommand
    @RequestMapping("/getProductOrderList")
    @ResponseBody
    public String getProductOrderList(String name){
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "--- "+name;
    }

   /* 在 Spring Cloud 中使用断路器 hystrix 后，可能会遇到异常
    com.netflix.hystrix.contrib.javanica.exception.FallbackDefinitionException: fallback method wasn't found
    这是因为指定的 备用方法 和 原方法 的参数个数，类型不同造成的
    所以需要统一参数的个数，类型*/

    private String fallback(String name) {
        return "网络开小差了，请稍后重试···";
    }
    private String defaultFallback() {
        return "defaultFallback: 网络开小差了，请稍后重试···";
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
