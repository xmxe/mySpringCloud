package com.cas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class CasClientTController {
    @RequestMapping("/c2")
    public String c1(){
        return "success-c2";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:http://192.168.236.131:8080/cas/logout?service=http://127.0.0.1:9101/";
    }
}

