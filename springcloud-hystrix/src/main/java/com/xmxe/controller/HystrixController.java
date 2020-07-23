package com.xmxe.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {

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
    这是因为指定的 fallback方法 和 原方法 的参数个数，类型不同造成的 所以需要统一参数的个数，类型*/

    private String fallback(String name) {
        return "自定义fallback:网络开小差了，请稍后重试···";
    }
    private String defaultFallback() {
        return "defaultFallback: 网络开小差了，请稍后重试···";
    }

}
