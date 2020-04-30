package com.wgx.study.zuul.filter;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class ErrorZuulFallback implements FallbackProvider {

    private static final String SERVER_NAME = "project-service";

    /**
     * 指定该回调类生效的路由服务名称
     *
     * @return Eureka中注册的服务id，如果需要路由所有服务都支持回退，则return"*"或return null
     */
    @Override
    public String getRoute() {
        return SERVER_NAME;
    }

    /**
     * 具体的回调逻辑
     *
     * @param route
     * @param cause
     * @return
     */
	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {

        System.out.println(route);

		//标记不同的异常为不同的http状态值
		if (cause instanceof HystrixTimeoutException) {
			return response(HttpStatus.GATEWAY_TIMEOUT);
		} else {
			//可继续添加自定义异常类
			return response(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//处理
	private ClientHttpResponse response(final HttpStatus status) {

		String msg="该"+SERVER_NAME+"服务暂时不可用!";

        return new ClientHttpResponse() {

            /**
             * @return 响应的Http状态码
             * @throws IOException
             */
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return status;
            }

            /**
             * @return 响应的Http状态码
             * @throws IOException
             */
            @Override
            public int getRawStatusCode() throws IOException {
                return status.value();
            }

            /**
             * @return 响应的Http状态文本
             * @throws IOException
             */
            @Override
            public String getStatusText() throws IOException {
                return status.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            /**
             * @return 响应体
             * @throws IOException
             */
            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(msg.getBytes());
            }

            /**
             * @return 响应头
             */
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }

}