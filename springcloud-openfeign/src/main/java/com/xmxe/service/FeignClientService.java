package com.xmxe.service;

import com.xmxe.config.FeiginConfig;
import com.xmxe.hystrix.ProducerRemoteHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
// 远程调用 服务名不允许带'_'
@FeignClient(qualifier = "feginQualifier",value = "provider-service",url = "http://localhost:8085/",
        fallback = ProducerRemoteHystrix.class,configuration = FeiginConfig.class,decode404 = true)
/*
 * name是value的别名，value也是name的别名。两者的作用是一致的，name指定FeignClient的名称，如果项目使用了Ribbon，name属性会作为微服务的名称，用于服务发现。
 * qualifier属性用来指定@Qualifier注解的值
 * url 调试时使用 指定请求的服务地址 此时name value无效
 * decode404当feign请求的接口(不是spring mvc的请求接口)发生http 404错误时，如果该字段位true，会调用decoder进行解码，否则抛出FeignException(假如未指定fallback)。
 * fallback：定义容错的处理类，当调用远程接口失败或超时时，会调用对应接口的容错逻辑，fallback指定的类必须实现@FeignClient标记的接口。
 * fallbackFactory：工厂类，用于生成fallback类示例，通过这个属性我们可以实现每个接口通用的容错逻辑，减少重复的代码。
 * path属性定义当前FeignClient的统一前缀。这样方便在该FeignClient中的@RequestMapping中书写value值
 */
public interface FeignClientService {

    @GetMapping("/hello_feign")
    String hello_feign(@RequestParam(value = "name") String name);

    // @PathVariable("id") 中的”id”，不能省略，必须指定。
    @RequestMapping(value = "/simple/{id}", method = RequestMethod.GET)
    String findById(@PathVariable("id") Long id);

    // feign多参数调用不允许使用对象接收
    // String queryBy(User user);//该请求不会成功，只要参数是复杂对象,用下面的方式
    @RequestMapping(value = "/query-by", method = RequestMethod.GET)
    List<String> queryBy(@RequestParam Map<String, Object> param);

}