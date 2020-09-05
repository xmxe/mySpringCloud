package com.xmxe.controller;

import com.xmxe.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController {

    @Autowired
    LoginService loginService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @GetMapping("admin/getData")
    @ResponseBody
    public String getAdminData() {
        return "adminData";
    }

    @GetMapping("auth/getData")
    @ResponseBody
    public String getAuthData() {
        return "authData";
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


}
