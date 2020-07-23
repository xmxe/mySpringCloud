package com.xmxe.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import reactor.core.publisher.Mono;

//@Configuration //限流 通过redis的key来实现
public class GateWayConfig {
    //@Bean
    public KeyResolver ipKeyResolver() {//设置根据请求 IP 地址来限流
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
        //return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
        //return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));//根据请求参数中的 user 字段来限流
        //return exchange -> Mono.just(exchange.getRequest().getURI().getPath());//根据uri限流
    }
}
