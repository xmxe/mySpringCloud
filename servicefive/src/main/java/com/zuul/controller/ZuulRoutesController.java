package com.zuul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@RestController //注意：要跳转到html页面@controller很重要，不要使用@restcontroller，因为他是返回json
@Controller
public class ZuulRoutesController {

    @RequestMapping("/index")
    public String hello() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}
