package com.xmxe.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import reactor.core.publisher.Mono;

//@Configuration
public class GateWayConfig {
    /*
     * 限流 通过redis的key来实现
     */
    //@Bean
    public KeyResolver ipKeyResolver() {//设置根据请求 IP 地址来限流
       /* return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                return Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
            }
        };*/

        // 根据主机名限流
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
        // 根据远程地址ip限流
        //return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());

        // 根据请求参数中的 user 字段来限流
        // return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));

        // 根据uri限流
        // return exchange -> Mono.just(exchange.getRequest().getURI().getPath());
    }


}
