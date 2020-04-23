package com.wgx.study.project.事务的隔离级别和传播行为以及Spring事务;

import com.wgx.study.project.common.mapper.UserMapper;
import com.wgx.study.project.common.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TransactionService transactional;

    @Transactional
    public void aTransactional() {
        User user1 = new User();
        user1.setUserName("张三");
        user1.setUserAge("18");
        userMapper.insertUser(user1);
        try {
            transactional.bTransactional();//TODO !!! 如果A和B位于同一个类，不要使用this(或直接)调用，而是应该通过注入本类对象调用。否则会出现与预期结果不符的情况。
        } catch (Exception e) {
            System.out.println("B中出现异常，回滚B");
        }
        System.out.println(1 / 0);
        //transactional.bTransactional();
    }

    /**
     * REQUIRED(Spring默认的传播行为)
     * 如果存在当前事务，则在当前事务中执行。否则开启一个新的事务执行。
     * B单独执行，则开启B事务执行。A调用B，则B和A都在A事务中执行。
     */
    //@Transactional(propagation = Propagation.REQUIRED)//REQUIRED

    /**
     * REQUIRES_NEW
     * 无论是否存在当前事务，都开启一个新的事务执行。
     * B单独执行，则开启B事务执行。A调用B，则开启B事务执行B，此时如果有A事务，则A事务挂起。
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)//REQUIRES_NEW
    public void bTransactional() {
        User user2 = new User();
        user2.setUserName("李四");
        user2.setUserAge("14");
        userMapper.insertUser(user2);
        try {
            System.out.println(1 / 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
/*
 * 事务方法A调用事务方法B(只考虑两个方法都执行到的情况)：
 *
 * REQUIRED(Spring默认)：
 * B异常，B捕获，B和A都正常提交。                            B(×->√)-->A(√->√)：都提交
 * B异常，B未捕获，B和A都回滚(无论A中是否捕获)。             B(×->×)-->A(×->×/√)：都回滚
 * 两个事务方法只要有任意一方未捕获异常，就都回滚。否则就都正常提交。
 * 内层方法和外层方法共享同一事务，最终结果必然一致。只要有一方将异常暴露出去(到方法外)就都回滚，否则就都提交。
 *
 * REQUIRES_NEW：
 * B异常，B未捕获，A未捕获，B和A都回滚。                     B(×->×)-->A(×->×)：都回滚
 * B异常，B未捕获，A捕获，B回滚，A正常提交。                 B(×->×)-->A(×->√)：B回滚，A提交
 * B异常，B捕获，B和A都正常提交。                            B(×->√)-->A(√->√)：都提交
 * A异常，B正常或捕获B异常，A未捕获，A回滚，B正常提交。      B(×/√->√)-->A(×->×)：B提交，A回滚
 * A异常，B正常或捕获B异常，A捕获，A和B都正常提交            B(×/√->√)-->A(×->√)：都提交
 * 未捕获异常的事务方法回滚，捕获异常或正常的事务方法正常提交
 * 内层方法和外层方法分别处于不同事务，最终结果没有必然关系。将异常暴露出去(到方法外)的一方回滚，否则提交。
 *
 * 可以继续推广到3个及以上的事务方法调用
 * */

//添加事务@Transactional(rollbackFor = Exception.class)不管检查异常还是非检查异常都会回滚。
//添加事务注解的方法必须声明为public方法。