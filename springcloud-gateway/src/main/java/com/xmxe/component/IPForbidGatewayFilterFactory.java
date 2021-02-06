package com.xmxe.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class IPForbidGatewayFilterFactory extends AbstractGatewayFilterFactory<IPForbidGatewayFilterFactory.Config> {

	public IPForbidGatewayFilterFactory() {
		super(Config.class);
	}
	@Override
	public GatewayFilter apply(Config config) {
		log.info("进入IPForbidGatewayFilterFactory apply");
		return (exchange, chain) -> {
			// 指定ip访问 在配置文件里写上允许访问的ip
			String ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
			if (config.getForbidIp().equals(ip)) {
				return chain.filter(exchange);
			}
			exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
			return exchange.getResponse().setComplete();

		};
	}

	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList("forbidIp");
	}

	static class Config{
		private String forbidIp;

		public String getForbidIp() {
			return forbidIp;
		}

		public void setForbidIp(String forbidIp) {
			this.forbidIp = forbidIp;
		}
	}
}
