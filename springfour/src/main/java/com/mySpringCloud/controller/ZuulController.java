package com.mySpringCloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class ZuulController {

    @RequestMapping("/hi")
    public String hi(String name){return "负载均衡，hi,这是由servicetwo提供的，将由ribbon调用-- "+name;}

}
