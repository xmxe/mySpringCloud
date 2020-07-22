package com.xmxe.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

    @RequestMapping("hello_ribbon")
    public String hello_ribbon(String name){
        return  "hello_ribbon-----------"+name;
    }

    @RequestMapping("hello_feign")
    public String hello_feign(String name){ return  "hello_feign-----------"+name; }
}
