package com.xmxe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class RibbonController {
    @Autowired
    RestTemplate restTemplate;

    //@Autowired
    //HttpServletRequest request;

    @RequestMapping("/hello")
    @ResponseBody
	public String hello(String name){return "hello,这是由serviceone发布的,会由feign调用--- "+name;}

	//访问此接口调用其他注册的微服务
    @RequestMapping("/sone")
    @ResponseBody
    public String hi(@RequestParam String name){
        return restTemplate.getForObject("http://servicetwo/hi?name="+name, String.class);
    }
    @RequestMapping("/index")
    public String hello() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/session_redis")
    @ResponseBody
    public String redis_session(HttpServletRequest request){
        //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("name");
        if(StringUtils.isEmpty(name)){
            name = "负载均衡testSessionRedis|" + System.currentTimeMillis();
            session.setAttribute("name", name);
        }
        System.out.println("访问端口：" + request.getServerPort());
        System.out.println(request.getParameter("name"));
        return name;
    }
}
