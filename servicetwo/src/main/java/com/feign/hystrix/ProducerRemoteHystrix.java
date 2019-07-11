package com.feign.hystrix;

import com.feign.service.FeignClientService;
import org.springframework.stereotype.Component;

@Component
public class ProducerRemoteHystrix implements FeignClientService {

    @Override
    public String serviceOne(String name) {
        return "feign调用失败";
    }

    @Override
    public String session_redis() {
        return "session redis调用失败";
    }
}