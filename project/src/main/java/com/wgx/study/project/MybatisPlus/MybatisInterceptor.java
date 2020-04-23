package com.wgx.study.project.MybatisPlus;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * Mybatis原生拦截器(插件)
 * TODO 注意：SpringMVC的拦截器实现的是HandlerInterceptor接口，不要搞混
 */
@Slf4j
@Intercepts({@Signature(
        type = Executor.class,

        //拦截更新(增删改)操作
        //method = "update",
        //args = {MappedStatement.class, Object.class}
        //拦截查询操作
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}

        )})
public class MybatisInterceptor implements Interceptor {


    /**
     * 拦截方法，具体业务逻辑编写的位置
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("Mybatis拦截器执行");
        return invocation.proceed();
    }

    /**
     * 创建target对象的代理对象,目的是将当前拦截器加入到该对象中
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 属性设置
     */
    @Override
    public void setProperties(Properties properties) {

    }
}
