package com.xmxe.component;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局过滤器的用法：实现 GlobalFilter 和 Ordered，重写相关方法，加入到spring容器管理即可，无需配置，全局过滤器对所有的路由都有效。
 */
@Slf4j
@Component
public class FluidControlGlobalGatewayFilter implements GlobalFilter, Ordered {

	// 限流 每秒往桶里扔5个令牌
	private static RateLimiter limiter = RateLimiter.create(5);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("进入FluidControlGlobalGatewayFilter filter");
		// 尝试获取令牌 不回阻塞 没有直接返回401
		if(limiter.tryAcquire()){
			return chain.filter(exchange);
		}else{
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
