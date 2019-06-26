package com.mySpringCloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@RestController //注意：要跳转到html页面@controller很重要，不要使用@restcontroller，因为他是返回json
@Controller
public class SecurityController {

    @RequestMapping("/hi")
    @ResponseBody
    public String hi(String name){return "负载均衡，hi,这是由servicetwo提供的，将由ribbon调用-- "+name;}

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("admin/getData")
    @ResponseBody
    public String getAdminData() {
        return "adminData";
    }

    @GetMapping("guest/getData")
    @ResponseBody
    public String getGuestData() {
        return "guestData";
    }

    @GetMapping("permission1/getData")
    @ResponseBody
    public String getPermission1Data() {
        return "permission1Data";
    }

    @GetMapping("permission2/getData")
    @ResponseBody
    public String getPermission2Data() {
        return "permission2Data";
    }

    @GetMapping("permission3/getData")
    @ResponseBody
    public String getPermission3Data() {
        return "permission3Data";
    }

    @GetMapping("permission4/getData")
    @ResponseBody
    public String getPermission4Data() {
        return "permission4Data";
    }

}
