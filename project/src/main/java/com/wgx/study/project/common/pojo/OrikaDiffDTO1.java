package com.wgx.study.project.common.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrikaDiffDTO1 {
    private String name1;
    private Integer age1;
    private Integer sex;
}
