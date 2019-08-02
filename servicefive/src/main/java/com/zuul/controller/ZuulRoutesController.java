package com.zuul.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@RestController //注意：要跳转到html页面@controller很重要，不要使用@restcontroller，因为他是返回json
@Controller
@RefreshScope
public class ZuulRoutesController {

    @RequestMapping("/index")
    public String hello() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @Value("${jdbcUrl}")
    String jdbcUrl;

    @RequestMapping("/getJdbcUrl")
    @ResponseBody
    public String getJdbcUrl() {
        return "jdbcUrl2:" + jdbcUrl;
    }
}
