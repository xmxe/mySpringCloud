package com.feign.service;

import com.feign.hystrix.ProducerRemoteHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "serviceone",fallback = ProducerRemoteHystrix.class)//对应serviceone服务
public interface FeignClientService {
    //对应serviceone服务中的hello接口
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    String serviceOne(@RequestParam(value = "name") String name);
}