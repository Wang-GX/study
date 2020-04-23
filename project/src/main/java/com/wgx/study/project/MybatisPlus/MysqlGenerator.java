package com.wgx.study.project.MybatisPlus;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * mysql 代码生成器演示例子
 * </p>
 */
public class MysqlGenerator {

    //数据库配置
    private static final String DRIVERNAME = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/local_test?useUnicode=true&useSSL=false&characterEncoding=utf8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    /**
     * RUN THIS
     */
    public static void main(String[] args) {
        //代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        //gc.setFileOverride(true);//是否覆盖文件
        gc.setOpen(false);
        gc.setAuthor("wgx");
        gc.setSwagger2(true);
        gc.setActiveRecord(true);
        //gc.setBaseResultMap(true);
        //gc.setBaseColumnList(true);
        gc.setServiceName("%sService");//自定义文件命名，注意 %s 会自动填充表实体属性！
        mpg.setGlobalConfig(gc);

        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDriverName(DRIVERNAME);
        dsc.setUrl(URL);
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASSWORD);
        mpg.setDataSource(dsc);

        //包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.wjj.application");
        mpg.setPackageInfo(pc);

        //策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        //添加乐观锁版本号注解
        strategy.setVersionFieldName("version");
        //添加逻辑删除注解
        strategy.setLogicDeleteFieldName("deleted");
        //添加自动填充属性
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
        tableFillList.add(new TableFill("create_user", FieldFill.INSERT));
        tableFillList.add(new TableFill("deleted", FieldFill.INSERT));
        tableFillList.add(new TableFill("update_time", FieldFill.UPDATE));
        tableFillList.add(new TableFill("update_user", FieldFill.UPDATE));
        strategy.setTableFillList(tableFillList);
        mpg.setStrategy(strategy);
        //mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}