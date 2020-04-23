package com.wgx.study.project.ExcelTest;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.wgx.study.project.Excel.DemoData;
import com.wgx.study.project.Excel.DemoDataListener;
import com.wgx.study.project.common.mapper.ExcelMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 读的常见写法
 * excel文件 -> 内存 -> 保存到数据库
 *
 * @author Jiaju Zhuang
 */

@SpringBootTest
public class ReadTest {

    private static final String fileName = System.getProperty("user.dir") + "/src/main/resources/" + "ReadDemoExcel" + ".xlsx";

    @Autowired
    private ExcelMapper excelMapper;

    /**
     * 最简单的读(只读取单一sheet)
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 由于默认异步读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>
     * 3. 直接读即可
     */
    @Test
    public void simpleRead() {
        //有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        //这里需要指定读用哪个class去读，选择要读取的sheet，文件流会自动关闭
        //注意：sheet的索引和名字要对应上，否则可能读取到多个sheet的数据(例如：索引0 + 索引1的名字)
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener(this.excelMapper)).sheet(0, "模板1").doRead();
    }

    /**
     * 读取多个sheet
     * 需要手动关闭流
     */
    @Test
    public void multipleSheetRead() {
        ExcelReader excelReader = EasyExcel.read(fileName).build();
        //TODO 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        ReadSheet readSheet1 = EasyExcel.readSheet(0, "模板1").head(DemoData.class).registerReadListener(new DemoDataListener(this.excelMapper)).build();
        ReadSheet readSheet2 = EasyExcel.readSheet(1, "模板2").head(DemoData.class).registerReadListener(new DemoDataListener(this.excelMapper)).build();
        excelReader.read(readSheet1, readSheet2);//该方法有多种简单的重载形式，可以选择使用
        excelReader.finish();
    }

    /**
     * 读取全部sheet
     */
    @Test
    public void allSheetRead() {
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener(this.excelMapper)).build().readAll();
    }

}

