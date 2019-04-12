package com.mySpringCloud.hystrix;

import org.springframework.stereotype.Component;

@Component
public class ProducerRemoteHystrix{
    public String getPort() {
        return "Producer Server 的服务调用失败";
    }
}
