package com.wgx.study.faced.distributed_transaction;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/testDistributedTransaction")
public class DistributedTransactionController {

    private static Logger logger = LoggerFactory.getLogger(DistributedTransactionController.class);

    @Autowired
    private DistributedTransactionFeignClient distributedTransactionFeignClient;

    /**
     * 支付成功 -> 减库存、生成订单
     * 请求地址：http://localhost:8004/testDistributedTransaction/purchase
     */
    @GlobalTransactional(name = "purchase", rollbackFor = Exception.class)
    @PostMapping("/purchase")
    public void purchase() {
        logger.info(" 全局事务 id ：" + RootContext.getXID());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        distributedTransactionFeignClient.stockTestDistributedTransaction(uuid);
        distributedTransactionFeignClient.orderTestDistributedTransaction(uuid);
        System.out.println(1/0);
    }

}