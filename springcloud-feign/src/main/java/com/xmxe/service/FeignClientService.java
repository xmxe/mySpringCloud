package com.xmxe.service;

import com.xmxe.config.FeiginConfig;
import com.xmxe.hystrix.ProducerRemoteHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "providerService",fallback = ProducerRemoteHystrix.class,configuration = FeiginConfig.class)
public interface FeignClientService {
    @RequestMapping(value = "/hello_feign", method = RequestMethod.GET)
    String hello_feign(@RequestParam(value = "name") String name);

}