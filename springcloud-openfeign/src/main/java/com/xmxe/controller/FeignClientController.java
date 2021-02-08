package com.xmxe.controller;

import com.xmxe.entity.User;
import com.xmxe.service.FeignClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class FeignClientController {

    @Autowired
    @Qualifier("feginQualifier")
    FeignClientService feignClient;

    //调用其他注册的微服务
    @GetMapping(value = "feignByGet")
    public String feignByGet(String name){
        log.info("findById返回->{}",feignClient.findById(23L));
        log.info("requestBody返回->{}",feignClient.requestBody("qqq","eee"));
        return feignClient.get_feign(name);
    }

    @GetMapping(value = "feignByPost")
    public String feignByPost(String name){
        User user = new User();
        user.setName("joge");
        log.info("queryBy返回->{}",feignClient.queryBy(user));
        return feignClient.post_feign(Map.of("name",name,"age",2));
    }

}
