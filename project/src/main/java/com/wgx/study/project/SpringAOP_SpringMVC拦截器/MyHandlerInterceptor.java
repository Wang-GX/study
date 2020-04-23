package com.wgx.study.project.SpringAOP_SpringMVC拦截器;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SpringMVC拦截器：只针对controller进行拦截
 * TODO 注意：拦截器是在请求到达controller之前和离开controller之后执行。即如果拦截器和AOP同时作用于controller中的方法，那么拦截器一定早于AOP的前置通知执行，晚于AOP的后置通知执行。
 * 前后端分离项目，视图解析器不生效，视图渲染后置方法略
 */
public class MyHandlerInterceptor implements HandlerInterceptor {

    /**
     * 前置方法：在处理器方法执行前执行
     *             ↓
     *          ----->controller----->
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("执行SpringMVC拦截器前置方法");
        System.out.println("被拦截的方法为：" + request.getMethod() + ":" + request.getRequestURI());
        return true;
    }

    /**
     * 后置方法：在处理器方法执行之后执行
     *                           ↓
     *         ----->controller----->
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("执行SpringMVC拦截器后置方法");
        System.out.println("被拦截的方法为：" + request.getMethod() + ":" + request.getRequestURI());
    }
}
