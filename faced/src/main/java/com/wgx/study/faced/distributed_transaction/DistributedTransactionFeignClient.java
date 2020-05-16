package com.wgx.study.faced.distributed_transaction;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "project-service", contextId = "example2")
public interface DistributedTransactionFeignClient {

    /**
     * 模拟生成订单
     *
     * @return
     */
    @PostMapping("/distributedTransaction/order")
    String orderTestDistributedTransaction(@RequestParam("id") String id);

    /**
     * 模拟扣减库存
     *
     * @return
     */
    @PostMapping("/distributedTransaction/stock")
    String stockTestDistributedTransaction(@RequestParam("id") String id);

}
