package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignClientController {
    @Autowired
    FeignClient client1;
    @GetMapping(value = "stwo")
    public String ttt(String name){
        //return "servicetwo"+
        return client1.serviceOne(name);
    }

}
