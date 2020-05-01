package com.wgx.study.zuul.filter;

import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;


/**
 * 自定义网关后置过滤器：处理返回值内容，添加响应头 啊啊
 */
//@Component
public class PostZuulFilter extends ZuulFilter {

    //是否执行过滤器，如果返回true则执行run方法，如果返回false则过滤器不生效
    @Override
    public boolean shouldFilter() {
        return true;
    }

    //过滤器执行逻辑
    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        try {
            //获取返回值内容，加以处理
            InputStream stream = context.getResponseDataStream();
            String responseStr = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            //你的处理逻辑，加密，添加新的返回值等等.....
            //内容重新写入
            context.setResponseBody(responseStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //指定过滤器的优先级，越小则优先级越高(可以使用枚举中定义的参数)
    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 3;
    }

    //指定过滤器的类型
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

}