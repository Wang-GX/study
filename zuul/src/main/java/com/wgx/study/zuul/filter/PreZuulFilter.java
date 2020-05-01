package com.wgx.study.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 自定义网关前置过滤器：校验请求参数是否合法，包含token参数
 */
@Component
public class PreZuulFilter extends ZuulFilter {

    private final Logger LOGGER = LoggerFactory.getLogger(PreZuulFilter.class);

    @Override
    public boolean shouldFilter() {
        //此方法可以根据请求的url进行判断是否需要拦截
        //如果是优先级最高的前置过滤器，那么不需要考虑请求上下文对象的响应状态码
        return !RequestContext.getCurrentContext().getRequest().getRequestURI().equals("/api/project-service/jwt/login");
    }

    @Override
    public Object run() throws ZuulException {
        //获取请求的上下文对象(注意是com.netflix.zuul.context包下的)
        //注意：这个上下文对象表示一次完整的请求-响应。
        RequestContext ctx = RequestContext.getCurrentContext();
        //获取request对象
        HttpServletRequest request = ctx.getRequest();
        //避免中文乱码
        ctx.addZuulResponseHeader("Content-type", "text/json;charset=UTF-8");
        ctx.getResponse().setCharacterEncoding("UTF-8");
        //打印日志
        LOGGER.info("请求方式：{},地址：{}", request.getMethod(), request.getRequestURI());
        //从请求头中获取token
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            LOGGER.info("非法访问");
            //设置为false时这个请求不会被Zuul路由到后端服务器，此时的执行流程参考doc目录下的：Zuul网关过滤器.jpg
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());//设置请求上下文的返回状态码，让下一个Filter看到当前Filter处理完毕后的状态(401：表示没有访问权限)
            ctx.setResponseBody("非法访问");
            //无任何意义的返回值，最终返回客户端的是RequestContext对象
            ctx.setThrowable(new Throwable("非法访问"));
            //throw new RuntimeException();//如果此处抛出异常，则请求不会路由到project服务，直接进入ERROR过滤器 -> POST过滤器 -> 结束
            return null;//如果此处返回null，表示不做任何处理，则请求不会进入ERROR过滤器。按照执行顺序应该接下来应该将请求路由到project服务，但是由于已经设置了setSendZuulResponse(false)，所以请求不会路由到project服务，直接进入POST过滤器 -> 结束
        }
        ctx.setSendZuulResponse(true);//对该请求进行路由
        ctx.setResponseStatusCode(HttpStatus.OK.value());//设置请求上下文的返回状态码，让下一个Filter看到当前Filter处理完毕后的状态
        //无任何意义的返回值，最终返回的是RequestContext
        return null;

    }

    @Override
    public String filterType() {
        //前置过滤器
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //执行顺序
        return 0;
    }

}