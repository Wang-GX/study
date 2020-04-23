package com.wgx.study.project.common.mapper;

import com.wgx.study.project.Excel.DemoData;
import org.apache.ibatis.annotations.Param;

public interface ExcelMapper {

    void save(@Param("demoData") DemoData demoData);
}