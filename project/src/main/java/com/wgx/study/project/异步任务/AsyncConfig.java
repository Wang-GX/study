package com.wgx.study.project.异步任务;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;

/**
 * Spring会优先从该类定义的线程池中获取空闲线程去执行@Async注解的异步任务
 * TODO 异步任务必须提供可用的线程池，SpringBoot默认引入ThreadPoolTaskScheduler(该线程池用于SpringSchedule定时任务)
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("线程%d").build();
        return new ThreadPoolExecutor(
                5,
                10,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}
