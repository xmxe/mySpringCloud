package com.xmxe.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/config")
@RefreshScope
public class NacosConfigController {

    @Value("${user.age}")
    private String userAge;
    @Value("${user.name}")
    private String userName;

    @RequestMapping("/age")
    public String age() {
        return userAge;
    }
    @RequestMapping("/name")
    public String name() {
        return userName;
    }
}
