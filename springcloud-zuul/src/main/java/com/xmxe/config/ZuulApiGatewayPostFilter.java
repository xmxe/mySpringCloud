package com.xmxe.config;

import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * 路由过滤器执行之后，执行后置过滤器打印日志
 */
@Component
public class ZuulApiGatewayPostFilter extends ZuulFilter {
    private static Logger log = LogManager.getLogger(ZuulApiGatewayPostFilter.class);

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        try (final InputStream responseDataStream = context.getResponseDataStream()) {
            if(responseDataStream == null) {
                log.warn("RESPONSE BODY: {}", "");
                return null;
            }
            String responseData = CharStreams.toString(new InputStreamReader(responseDataStream, "UTF-8"));
            log.info("RESPONSE BODY: {}", responseData);
            context.setResponseBody(responseData);
        }
        catch (Exception e) {
            throw new ZuulException(e, INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
        return null;
    }
}
