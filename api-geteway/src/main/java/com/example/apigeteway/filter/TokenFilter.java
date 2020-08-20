package com.example.apigeteway.filter;

import com.example.apigeteway.exception.RateLimitException;
import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;


@Component
public class TokenFilter extends ZuulFilter {

    private static final RateLimiter RATE_LIMITER = RateLimiter.create(1); // 限流 每秒 1 个令牌

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
//        return PRE_DECORATION_FILTER_ORDER - 1;
        return SERVLET_DETECTION_FILTER_ORDER - 1; // 越小 优先级 越高 这里限流  需要最高
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();
        response.setHeader("X-Foo", UUID.randomUUID().toString()); // 设置返回头

        // 从 参数  cookie header里面获取
        String token = request.getParameter("token");
        if(StringUtils.isEmpty(token)){
            requestContext.setSendZuulResponse(false); // 设置不通过
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

        // 如果没有拿到令牌 则抛错
        if(!RATE_LIMITER.tryAcquire()){
            throw new RateLimitException();
        }

        //  /order/create 只能买家访问

        //  /order/fin 只能卖家访问

        //  /order/create 都可以访问

        return null;
    }
}
