package com.ken.material.qcloud.im;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器入口类
 **/
public class SpringBootPlusGenerator {

    @Test
    public void generateCode() {
        generate("user", "tb_tag");
    }

    private void generate(String moduleName, String... tableNamesInclude) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("ken");
        gc.setDateType(DateType.ONLY_DATE);
        gc.setOpen(false);
        //默认不覆盖，如果文件存在，将不会再生成，配置true就是覆盖
//        gc.setFileOverride(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/material?useUnicode=true&serverTimezone=CTT&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent("com.ken.material.modules");
        mpg.setPackageInfo(pc);

        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        //templateConfig.setService("/templates/service.java");
        //templateConfig.setServiceImpl("/templates/serviceImpl.java");
        //templateConfig.setController("/templates/controller.java");

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.ken.material.common.entity.BaseEntity");
        strategy.setEntityLombokModel(true);
        //strategy.setSuperControllerClass("com.dty.entity.base.bind.BaseController");
        strategy.setRestControllerStyle(true);
        strategy.setInclude(tableNamesInclude);
        strategy.setSuperEntityColumns("id", "create_by", "create_time", "modify_by", "modify_time", "version");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("tb_");
        strategy.setEntityTableFieldAnnotationEnable(true);
        //strategy.setVersionFieldName("version");
        mpg.setStrategy(strategy);

        mpg.setTemplateEngine(new VelocityTemplateEngine());

        mpg.execute();
    }
}
