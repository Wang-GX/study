package com.wgx.study.project.SpringValidator;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "SpringValidated参数校验", tags = "SpringValidated参数校验")
@RestController
@RequestMapping("/springValidated")
public class SpringValidatorController {

    @PostMapping("testValidated")
    //注意：在对象参数前要添加@Valid注解，否则Validate相关注解不能生效
    public void testValidated(@RequestBody @Valid ValidatorEntity validatedTestEntity) {
        System.out.println(validatedTestEntity);
    }
}

