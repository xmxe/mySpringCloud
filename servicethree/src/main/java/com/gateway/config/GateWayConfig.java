package com.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import reactor.core.publisher.Mono;

//@Configuration
public class GateWayConfig {
    //@Bean
    public KeyResolver ipKeyResolver() {//设置根据请求 IP 地址来限流
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }
   /* KeyResolver userKeyResolver() {//根据请求参数中的 user 字段来限流
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
    }*/
}
