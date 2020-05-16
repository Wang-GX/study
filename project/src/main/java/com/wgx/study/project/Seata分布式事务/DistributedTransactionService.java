package com.wgx.study.project.Seata分布式事务;

import com.wgx.study.project.common.mapper.UserMapper;
import com.wgx.study.project.common.pojo.User;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DistributedTransactionService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void stockTestDistributedTransaction(String id) {
        log.info(" 全局事务 id ：" + RootContext.getXID());
        User user = new User();
        user.setUserName("扣减库存:" + id);
        userMapper.insertUser(user);
    }

    @Transactional
    public void orderTestDistributedTransaction(String id) {
        log.info(" 全局事务 id ：" + RootContext.getXID());
        User user = new User();
        user.setUserName("生成订单:" + id);
        userMapper.insertUser(user);
    }
}
