package com.mySpringCloud;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
@org.springframework.cloud.netflix.feign.FeignClient(value = "serviceone")
public interface FeignClient {
    //对应serviceone服务中的hello接口
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    String serviceOne(@RequestParam(value = "name") String name);
}
