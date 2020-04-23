package com.wgx.study.project.多线程_本地线程锁_线程池;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * 我们有两种常见的创建线程的方法，一种是继承Thread类，一种是实现Runnable的接口，Thread类其实也是实现了Runnable接口。
 * 但是我们创建这两种线程在运行结束后都会被虚拟机销毁，如果线程数量多的话，频繁的创建和销毁线程会大大浪费时间和效率，更重要的是浪费内存，因为正常来说线程执行完毕后死亡，线程对象变成垃圾。
 * 使用线程池可以让线程在执行完成任务后不立即被销毁，而是可以重复使用。
 */
@Service
@Slf4j
public class LocalLockService {

    /**
     * 本地线程锁测试
     * 测试说明：模拟卖票，本地创建两个线程共享100张票。如果不出现超卖或者多个线程卖同一张票的情况则表示锁有效。
     */
    public void localLockTest() {

        //创建线程池

        /**
         *  线程池的7个核心参数：
         *
         *  int corePoolSize,  　　　　　　　　　　　　　　//核心线程池的大小
         *  int maximumPoolSize,　　                  //线程池能创建最大的线程数量
         *  long keepAliveTime,　　　　　　　　　　     //非核心线程空闲时的最长存活时间
         *  TimeUnit unit,　　　　　　　　　　　　　    //keepAliveTime时间单位
         *  BlockingQueue<Runnable> workQueue,    //缓存队列，用来存放等待被执行的任务
         *  ThreadFactory threadFactory,　　　　  //执行程序创建新线程时使用的工厂
         *  RejectedExecutionHandler handler   //线程池中的创建的线程数量已经达到最大并且缓存队列已满时新任务的处理策略
         *      (1)ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
         *      (2)ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
         *      (3)ThreadPoolExecutor.DiscardOldestPolicy：丢弃缓存队列中最前面(最早添加)的任务，然后重新尝试执行任务(重复此过程)。
         *      (4)ThreadPoolExecutor.CallerRunsPolicy：由调用线程，而不是线程池中的线程处理该任务。
         *         https://www.jianshu.com/p/aa420c7df275
         *         https://blog.csdn.net/matrix_google/article/details/53440113
         *         注意：如果使用这种策略，那么在任务全部处理完毕之前，调用线程将会一直阻塞(因为在全部处理完毕之前，无法确定是否需要使用到调用线程)。如果是请求线程，将会一直得不到响应。
         *
         */

        /**
         * corePoolSize与maximumPoolSize举例理解
         *
         * 1、池中线程数小于corePoolSize，新的任务会创建新的线程执行，线程执行完毕进入线程缓存队列，等待再次执行
         * 2、池中线程数大于等于corePoolSize，workQueue未满，首选将新任务加入workQueue而不是添加新线程
         * 3、池中线程数大于等于corePoolSize，workQueue已满，但是线程数小于maximumPoolSize，添加新的线程来处理被添加的任务
         * 4、池中线程数大于等于corePoolSize，workQueue已满，并且线程数等于maximumPoolSize，新任务被拒绝，使用handler处理被拒绝的任务
         */

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("线程%d").build();
        ThreadPoolExecutor singleThreadPool = new ThreadPoolExecutor(
                5,
                10,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(5),//缓存队列的最大容量
                namedThreadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );


        for(int i = 0; i < 16; i++) {
            //手动将任务执行时间延长4s演示效果
            Work task = new Work();
            //从线程池中获取线程执行任务
            singleThreadPool.execute(task);
            System.out.println(
                    "线程池中线程数目：" + singleThreadPool.getPoolSize() +
                    "，队列中等待执行的任务数目："+ singleThreadPool.getQueue().size() +
                    "，已执行完成的任务数目：" + singleThreadPool.getCompletedTaskCount());
        }

        //关闭线程池
        singleThreadPool.shutdown();

    }

}

class Work implements Runnable {

    private int ticket = 10000;

    @Override
    public void run() {
        while (true) {
            synchronized (Lock.getLock()) {
                if (this.ticket > 0) {
                    try {
                        Thread.sleep(4000L);//sleep方法不释放锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "卖了第" + ticket + "张票");
                    ticket--;
                } else {
                    break;
                }
            }
        }
    }
}

class Lock {

    private Lock() {
    }

    private static Lock lock = new Lock();

    public static Lock getLock() {
        return lock;
    }
}