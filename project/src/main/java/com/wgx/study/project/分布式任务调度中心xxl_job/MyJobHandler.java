package com.wgx.study.project.分布式任务调度中心xxl_job;

import com.wgx.study.project.common.mapper.UserMapper;
import com.wgx.study.project.common.pojo.User;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * xxj-job任务处理器(Bean模式)
 * <p>
 * 开发步骤：
 * 1、继承"IJobHandler"："com.xxl.job.core.handler.IJobHandler"。
 * 2、注册到Spring容器：添加"@Component"注解，被Spring容器扫描为Bean实例。
 * 3、注册到执行器工厂：添加"@JobHandler(value="自定义jobhandler名称")"注解，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 4、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 */
@JobHandler(value = "MyJobHandler")
@Component
@Slf4j
public class MyJobHandler extends IJobHandler {

    @Autowired
    private UserMapper userMapper;

    /**
     * 调度任务逻辑
     *
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("处理器正在执行任务");//这行日志会输出到调度中心项目的控制台上，但是不会输出到当前项目的控制台上

        log.info("处理器正在执行任务");

        User user = new User();
        user.setUserName(new Date().toString());
        int count = this.userMapper.insert(user);

        if (count == 1) {
            return SUCCESS;
        } else {
            return FAIL;
        }

    }

}
