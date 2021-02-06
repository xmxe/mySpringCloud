package com.xmxe.component;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
// 配置文件这里只要写AuthGatewayFilter类名中GatewayFilter前面的部分就行,即Auth
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

	private static final Logger logger = LoggerFactory.getLogger(AuthGatewayFilterFactory.class);

	@Override
	public GatewayFilter apply(Object config) {
		return new AuthGatewayFilter();
	}

	/**
	 * 限流
	 * GatewayFilter是局部过滤器 需要在配置文件里体现
	 */
	static class AuthGatewayFilter implements GatewayFilter, Ordered{

		private static RateLimiter limiter = RateLimiter.create(5);

		@Override
		public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
			logger.info("进入AuthGatewayFilterFactory filter");
			if(limiter.tryAcquire()){
				return chain.filter(exchange);
			}else{
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return exchange.getResponse().setComplete();
			}

		}

		/*
		 * 值越小优先级越大
		 */
		@Override
		public int getOrder() {
			return 1;
		}
	}
}
