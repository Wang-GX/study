package com.wgx.study.project.MybatisPlus;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class MybatisPlusConfiguration {

    //配置分页插件
    //TODO 必须配置，否则调用selectPage方法时得到的是错误的数据
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


    //配置Mybatis自定义的拦截器插件
    //@Bean
    public MybatisInterceptor mybatisInterceptor() {
        return new MybatisInterceptor();
    }


    //配置Mybatis阻断插件(用来阻断全表更新、删除的操作)
    //当配置此插件后，如果执行了全表更新、删除的操作，则会抛出异常。异常信息分别为：Prohibition of table update operation/Prohibition of full table deletion
    //TODO 注意：该插件仅适用于开发环境，防止误操作，不适用于生产环境。
    //@Bean
    public SqlExplainInterceptor sqlExplainInterceptor() {
        List<ISqlParser> sqlParserList = new ArrayList<>();
        SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
        sqlParserList.add(new BlockAttackSqlParser());
        sqlExplainInterceptor.setSqlParserList(sqlParserList);
        return sqlExplainInterceptor;
    }


    //配置Mybatis性能分析插件(该插件于3.2.0版本移除)
    //当配置此插件后，会输出每条SQL语句的执行时间。可以设置SQL语句允许的最大执行时长，如果超过此时长则会抛出异常。异常信息为：The SQL execution time is too large, please optimize ！
    //TODO 注意：该插件仅适用于开发环境，进行SQL语句的性能优化，不适用于生产环境。
    //@Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        //设置SQL允许的最大执行时长
        performanceInterceptor.setMaxTime(1000);//单位：毫秒
        //设置是否格式化SQL 默认false
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }


    //配置乐观锁插件
    //TODO 注意：乐观锁插件只支持 updateById(id) 与 update(entity, wrapper) 方法

    /**
     * 悲观锁和乐观锁解决的问题：并发环境下，多个线程操作数据库中相同的数据时导致的问题
     * (1) 悲观锁：阻塞其他线程
     *     悲观锁是一种利用数据库内部提供的锁机制对更新的数据进行锁定。当一个线程持有了悲观锁之后，其他线程不能操作被锁定的数据。
     *     根据加锁对象的不同分为行锁和表锁（Mysql只有InnoDB存储引擎才有行锁，MyISAM只有表锁），以及加锁机制不同分为共享锁和排他锁。
     *      ①行锁和表锁：
     *          MyISAM 操作数据都是使用的表锁，更新一条记录就要锁整个表，因此性能较低，并发不高。但同时不会存在死锁问题。
     *          InnoDB 与 MyISAM 的最大不同有两点：一是支持事务；二是采用了行级锁。
     *          在 Mysql 中，行级锁并不是直接锁记录，而是锁索引。
     *          索引分为主键索引和非主键索引两种，如果一条sql 语句操作了主键索引，Mysql 就会锁定这条主键索引；如果一条语句操作了非主键索引，MySQL会先锁定该非主键索引，再锁定相关的主键索引。
     *          InnoDB 行锁是通过给索引项加锁实现的，如果没有索引，InnoDB 会通过隐藏的聚簇索引来对记录加锁。也就是说：如果不通过索引条件检索数据，那么InnoDB将对表中所有数据加锁，实际效果跟表锁一样。因为没有了索引，找到某一条记录就得扫描全表，要扫描全表，就得锁定表。
     *
     *      ②排他锁和共享锁
     *          数据库的增删改操作默认都会加排他锁(独占锁)，而查询不会加任何锁。
     *          排他锁：对某一资源加排他锁，自身可以进行增删改查，其他人无法进行任何操作。
     *                  select * from table for update
     *          共享锁：对某一资源加共享锁，自身可以读该资源，其他人也可以读该资源（也可以再继续加共享锁，即 共享锁可多个共存），但无法修改。要想修改就必须等所有共享锁都释放完之后。语法为：
     *                  select * from table lock in share mode
     *

     * (2) 乐观锁：不阻塞其他线程，而是判断是否存在其他线程的并发操作
     *     乐观锁不使用数据库内部提供的锁进行实现(乐观锁不是数据库自带的，需要我们自己来实现)，而是使用CAS原理，所以不会阻塞其他线程，从而提高并发能力。可以通过严格递增(更新时始终递增，否则可能会出现ABA问题)的版本号来实现。
     *     线程开始执行阶段就到数据库中查询并记录下数据当前的版本号，更新时从数据库中查询最新的版本号并与记录值进行对比，如果相同则表示在此期间该数据没有被修改过(即在当前线程操作这条数据期间没有其他线程操作过这条数据)，才会去更新，否则就不更新。
     *     使用乐观锁带来的问题是导致大量的更新失败，可以考虑乐观锁重入机制。
     *     乐观锁核心的SQL语句为：
     *     UPDATE user
     *     	SET user_sex = '2',
     *     	version = 3
     *     WHERE
     *     	id = 1
     *     	AND version = 2;
     *
     *
     *
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }


    //配置自定义SQL注入器
    @Bean
    public MySqlInjector mySqlInjector() {
        return new MySqlInjector();
    }


    //配置自定义SQL填充器
    @Bean
    public MyMetaObjectHandler myMetaObjectHandler(){
        return new MyMetaObjectHandler();
    }

}
