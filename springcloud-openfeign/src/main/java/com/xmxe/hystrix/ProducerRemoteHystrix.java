package com.xmxe.hystrix;

import com.xmxe.service.FeignClientService;
import org.springframework.stereotype.Component;

@Component
public class ProducerRemoteHystrix implements FeignClientService {

    @Override
    public String hello_feign(String name) {
        return "feign调用失败";
    }

}
