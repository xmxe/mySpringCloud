package com.xmxe.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RefreshScope//在客户端执行/refresh的时候就会更新此类下面的变量值
@Controller
public class ConfigClientController {
    @Value("${jdbcUrl}")
    String jdbcUrl;

    @RequestMapping("/getJdbcUrl")
    @ResponseBody
    public String getJdbcUrl() {
        return "jdbcUrl:" + jdbcUrl;
    }
}
