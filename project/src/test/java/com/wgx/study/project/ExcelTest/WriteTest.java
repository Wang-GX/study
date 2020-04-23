package com.wgx.study.project.ExcelTest;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.wgx.study.project.Excel.DemoData;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

/**
 * 写的常见写法
 * 数据库 -> 内存 -> 输出到excel文件
 *
 * @author Jiaju Zhuang
 */
@Ignore
public class WriteTest {

    /**
     * 最简单的写(只支持单sheet、无法实现文件的续写、不支持无对应实体类的属性(字段)值的输出)
     * TODO 注意：不添加@ExcelProperty注解的属性，将以属性名作为表头输出
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 直接写即可
     */
    @Test
    public void simpleWrite() {

        //1.文件输出路径
        String fileName = "E:\\测试表" + System.currentTimeMillis() + ".xlsx";

        //2.数据列表
        List<DemoData> dataList = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            //设置行数据(参考数据库，一行数据记录对应一个Java实体对象)
            //循环多少次，就表示向List集合中添加多少个对象，即向excel表中插入多少行数据
            //如果某个对象属性没有设置值或者值为null，则在excel中填充空白单元格
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            dataList.add(data);
        }

        //3.表头设置(非必须项。如果不设置，则实体类中所有非@ExcelIgnore注解标识的属性都会在excel中生成对应的表头)
        //3.1设置排除表头列表(excel不会生成排除的表头及下面的数据)
        Set<String> excludeColumnFiledNames = new HashSet<String>();
        excludeColumnFiledNames.add("date");

        //3.2设置展示表头列表(excel只会生成展示的表头及下面的数据)
        Set<String> includeColumnFiledNames = new HashSet<String>();
        includeColumnFiledNames.add("date");

        //4.写数据
        //方式1：使用ExcelWriterSheetBuilder的doWrite方法(使用这种方式不需要手动关闭流，这种方式通常用于单一sheet的写)
        EasyExcel.write(fileName, DemoData.class).sheet(0,"模板").doWrite(dataList);
        //EasyExcel.write(fileName, DemoData.class).excludeColumnFiledNames(excludeColumnFiledNames).sheet("模板").doWrite(dataList);//数据列表
        //EasyExcel.write(fileName, DemoData.class).includeColumnFiledNames(includeColumnFiledNames).sheet("模板").doWrite(dataList);//数据列表

        //方式2：使用ExcelWriter的write方法(注意：使用这种方式必须手动关闭流)
        ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(0,"模板").build();
        //WriteSheet writeSheet = EasyExcel.writerSheet(0,"模板").excludeColumnFiledNames(excludeColumnFiledNames).build();
        //WriteSheet writeSheet = EasyExcel.writerSheet(0,"模板").includeColumnFiledNames(includeColumnFiledNames).build();
        excelWriter.write(dataList, writeSheet);
        excelWriter.finish();


    }

    /**
     * 多个sheet的写
     */
    @Test
    public void MultipleSheetWrite() {

        //1.文件输出路径
        String fileName = "E:\\测试表" + System.currentTimeMillis() + ".xlsx";

        //2.数据列表
        List<DemoData> dataListSheet1 = new ArrayList<DemoData>();
        List<DemoData> dataListSheet2 = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            //设置行数据(参考数据库，一行数据记录对应一个Java实体对象)
            //循环多少次，就表示向List集合中添加多少个对象，即向excel表中插入多少行数据
            //如果某个对象属性没有设置值或者值为null，则在excel中填充空白单元格
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            dataListSheet1.add(data);
            dataListSheet2.add(data);
        }


        //4.写数据
        ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build();
        WriteSheet writeSheet1 = EasyExcel.writerSheet(0,"模板1").build();
        WriteSheet writeSheet2 = EasyExcel.writerSheet(1,"模板2").build();
        excelWriter.write(dataListSheet1, writeSheet1).write(dataListSheet2, writeSheet2);
        excelWriter.finish();
    }

}
