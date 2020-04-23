package com.wgx.study.project.SpringValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.List;

/**
 * 部分注解在SpringBoot2.0中已被弃用
 *
 * swagger测试json：
 *  {
 *  	"age": 10,
 *  	"dateList": ["2012-01-07"],
 *  	"email": "523373295@qq.com",
 *  	"phoneNo": "17521358767",
 *  	"userName": "张三"
 *  }
 */
@ApiModel("SpringValidator测试实体类")
@Data
public class ValidatorEntity {

    @NotBlank(message = "员工姓名不能为空")
    @Length(max = 3, message = "员工姓名长度超过限制")
    @ApiModelProperty(value = "员工姓名", required = true)
    private String userName;

    @NotNull(message = "员工年龄不能为空")
    @Min(value = 0)
    @Max(value = 100)
    @ApiModelProperty(value = "员工年龄", required = true)
    private Integer age;

    @NotEmpty(message = "日期列表不能为空")
    @ApiModelProperty(value = "日期列表", required = true)
    private List<String> dateList;

    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号码格式不正确")
    @ApiModelProperty(value = "手机号", required = true)
    private String phoneNo;

    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

}