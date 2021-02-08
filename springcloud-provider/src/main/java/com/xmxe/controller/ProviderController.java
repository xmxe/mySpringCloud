package com.xmxe.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class ProviderController {

    @GetMapping("get_ribbon")
    public String get_ribbon(String name){
        return  "get_ribbon-----------"+name;
    }

    @PostMapping("post_ribbon")
    public String post_ribbon(String name){
        return  "post_ribbon-----------"+name;
    }

    @RequestMapping("hello_feign")
    public String hello_feign(String name){ return  "hello_feign-----------"+name; }

    @RequestMapping("/session_redis2")
    public String redis_session(HttpServletRequest request){
        //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("name");
        if(StringUtils.isEmpty(name)){
            name = "SessionRedis2|" + System.currentTimeMillis();
            session.setAttribute("name", name);
        }
        System.out.println("访问端口：" + request.getServerPort());
        return name;
    }
}
