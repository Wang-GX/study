package com.wgx.study.zuul.filter;

import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;


@Component
public class ErrorZuulFilter extends SendErrorFilter {

	private final Logger LOGGER = LoggerFactory.getLogger(ErrorZuulFilter.class);

	@Override
	public Object run() {
		String msg="请求失败！";
		RequestContext ctx = RequestContext.getCurrentContext();
		try{
			ExceptionHolder exception = findZuulException(ctx.getThrowable());
			LOGGER.error("错误信息:"+exception.getErrorCause());
			msg+="error:"+exception.getErrorCause();
			HttpServletResponse response = ctx.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.getOutputStream().write(msg.getBytes());
		}catch (Exception ex) {
			ex.printStackTrace();
			ReflectionUtils.rethrowRuntimeException(ex);
		} finally {
			ctx.remove("throwable");
		}
		//无任何意义的返回值，最终返回的是RequestContext
		return null;
	}

	@Override
	public int filterOrder() {
		return -1;
	}
}