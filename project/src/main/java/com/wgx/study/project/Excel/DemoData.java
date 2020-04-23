package com.wgx.study.project.Excel;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * 基础数据类，如果是读取excel，则这里的排序必须和excel里面的排序一致
 * 可以通过@ExcelProperty注解的index值指定字段在excel中的位置，索引从0开始
 *
 * @author Jiaju Zhuang
 **/
@Data
public class DemoData {
    @ExcelProperty(value = "字符串标题",index = 0)
    private String string;
    @ExcelProperty(value = "日期标题",index = 1)
    private Date date;
    @ExcelProperty(value = "数字标题",index = 2)
    private Double doubleData;
    /**
     * 忽略这个字段(该字段默认不会被输出到excel中，无需手动添加到排除表头列表中)
     */
    @ExcelIgnore
    private String ignore;

}