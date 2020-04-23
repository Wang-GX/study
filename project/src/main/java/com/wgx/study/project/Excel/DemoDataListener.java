package com.wgx.study.project.Excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import com.wgx.study.project.common.mapper.ExcelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板的读取监听器
 *
 * @author Jiaju Zhuang
 */
//TODO 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class DemoDataListener extends AnalysisEventListener<DemoData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoDataListener.class);

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    private List<DemoData> list = new ArrayList<>();
    private ExcelMapper excelMapper;


    /**
     * 如果使用了spring，请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     */
    public DemoDataListener(ExcelMapper excelMapper) {
        this.excelMapper = excelMapper;
    }


    /**
     * 每一条数据解析都会调用
     */
    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM(内存溢出)
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
            LOGGER.info("list已清空");
        }
    }

    /**
     * 所有数据解析完成调用
     * 如果有多个sheet，每解析完一个sheet调用一次
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        if (list.size() > 0){
            //!注意：如果解析完成一个sheet时集合中仍然存在读取到的数据，则需要在这些数据保存完毕后立即清空集合，否则在读取下一个sheet时会将之前sheet的遗留数据重复插入到数据库中。
            list.clear();
        }
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 将读取到的数据存入数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        for (DemoData demoData : list) {
            this.excelMapper.save(demoData);
        }
        LOGGER.info("存储数据库成功！");
    }

    /**
     * 当任何一个侦听器执行错误报告时，所有侦听器都将接收此方法。如果在此处引发异常，则整个读取将终止。
     *
     * @param exception 异常信息
     * @param context   上下文
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        LOGGER.info("读取数据发生异常:");
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            System.out.println(String.format("第%s行，第%s列解析异常，异常数据:%s",
                    excelDataConvertException.getRowIndex(), excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData()));
        }
    }
}