package com.wgx.study.project.Seata分布式事务;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Seata分布式事务",tags = "Seata分布式事务")
@RestController
@RequestMapping("/distributedTransaction")
public class DistributedTransactionController {

    @Autowired
    private DistributedTransactionService distributedTransactionService;

    /**
     * 模拟生成订单
     *
     * @return
     */
    @PostMapping("/order")
    String orderTestDistributedTransaction(@RequestParam String id){
        distributedTransactionService.orderTestDistributedTransaction(id);
        return null;
    };

    /**
     * 模拟扣减库存
     *
     * @return
     */
    @PostMapping("/stock")
    String stockTestDistributedTransaction(@RequestParam String id){
        distributedTransactionService.stockTestDistributedTransaction(id);
        return null;
    };
}
