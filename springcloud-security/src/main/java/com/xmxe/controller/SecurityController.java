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

    @GetMapping("admin/1")
    @ResponseBody
    public String getAdminData() {
        return "adminData";
    }

    @GetMapping("authenticated/1")
    @ResponseBody
    public String getAuthData() {
        return "authData";
    }

    @GetMapping("guest/1")
    @ResponseBody
    public String getGuestData() {
        return "guestData";
    }

    @GetMapping("permission/1")
    @ResponseBody
    public String getPermission1Data() {
        return "permissionData";
    }

    @GetMapping("ipaddress/1")
    @ResponseBody
    public String getPermission2Data() {
        return "ipaddressData";
    }
}
