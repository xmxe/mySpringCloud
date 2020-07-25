package com.xmxe.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class ProviderController {

    @RequestMapping("hello_ribbon")
    public String hello_ribbon(String name){
        return  "hello_ribbon-----------"+name;
    }

    @RequestMapping("hello_feign")
    public String hello_feign(String name){ return  "hello_feign-----------"+name; }

    @RequestMapping("/session_redis2")
    @ResponseBody
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
