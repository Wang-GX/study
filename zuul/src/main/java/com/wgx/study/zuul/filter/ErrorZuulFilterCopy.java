//package com.wgx.study.zuul.filter;
//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ReflectionUtils;
//
//import javax.servlet.http.HttpServletResponse;
//
//
//@Component
//public class ErrorZuulFilterCopy extends ZuulFilter {
//
//    private final Logger LOGGER = LoggerFactory.getLogger(ErrorZuulFilterCopy.class);
//
//    @Override
//    public boolean shouldFilter() {
//        //只有在Zuul网关过滤器执行过程中抛出异常时才会触发ERROR过滤器
//        return RequestContext.getCurrentContext().containsKey("throwable");
//    }
//
//    @Override
//    public Object run() throws ZuulException {
//        String msg = "请求失败！";
//        RequestContext ctx = RequestContext.getCurrentContext();
//        try {
//            Throwable throwable = RequestContext.getCurrentContext().getThrowable();
//            LOGGER.error("错误信息:" + throwable.getMessage());
//            msg += "error:" + throwable.getMessage();
//            HttpServletResponse response = ctx.getResponse();
//            response.setCharacterEncoding("UTF-8");
//            response.getOutputStream().write(msg.getBytes());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            ReflectionUtils.rethrowRuntimeException(ex);
//        } finally {
//            ctx.remove("throwable");
//        }
//        //无任何意义的返回值，最终返回的是RequestContext
//        return null;
//    }
//
//
//    @Override
//    public String filterType() {
//        return FilterConstants.ERROR_TYPE;
//    }
//
//    @Override
//    public int filterOrder() {
//        return -2;
//    }
//}
