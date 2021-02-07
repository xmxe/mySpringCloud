package com.xmxe.hystrix;

import com.xmxe.service.FeignClientService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProducerRemoteHystrix implements FeignClientService {

    @Override
    public String hello_feign(String name) {
        return "hell0_feign接口调用失败";
    }

    @Override
    public String findById(Long id) {
        return "findById接口调用失败";
    }

    @Override
    public List<String> queryBy(Map<String, Object> param) {
        return null;
    }

}
