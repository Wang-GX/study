package com.wgx.study.project.事务的隔离级别和传播行为以及Spring事务;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 事务传播行为（propagation behavior）指的就是当一个事务方法(以下都使用事务方法B表示)被另一个事务方法(以下都使用事务方法A表示)调用时，这个事务方法应该如何进行。
 * 参考：https://blog.csdn.net/weixin_39625809/article/details/80707695
 */
@Api(value = "Spring事务",tags = "Spring事务")
@RestController
@RequestMapping("/springTransaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @ApiOperation(value = "Spring传播行为测试")
    @PostMapping("/propagationBehavior")
    public void propagationBehavior(){
        //执行A事务方法
        transactionService.aTransactional();
    }

}