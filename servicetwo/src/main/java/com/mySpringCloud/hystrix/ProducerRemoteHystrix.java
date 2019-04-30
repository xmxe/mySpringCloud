package com.mySpringCloud.hystrix;

import com.mySpringCloud.service.FeignClientService;
import org.springframework.stereotype.Component;

@Component
public class ProducerRemoteHystrix implements FeignClientService{

    @Override
    public String serviceOne(String name) {
        return "feign调用失败";
    }
}
