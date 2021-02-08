package com.xmxe.hystrix;

import com.xmxe.entity.User;
import com.xmxe.service.FeignClientService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProducerRemoteHystrix implements FeignClientService {

    @Override
    public String get_feign(String name) {
        return "get_feign接口调用失败";
    }

    @Override
    public String post_feign(Map<String,Object> params) {
        return "post_feign接口调用失败";
    }

    @Override
    public String queryBy(User user) {
        return "queryBy接口调用失败";
    }

    @Override
    public String requestBody(String name, String age) {
        return "requestBody调用失败";
    }

    @Override
    public String findById(Long id) {
        return "findById接口调用失败";
    }


}
