//package com.self.highperformance.code;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//
///**
// * 使用Mybatis-Plus进行代码生成
// */
//public class MyBatisPlusCode {
//
//    public static void main(String[] args) {
//        // 代码生成器
//        AutoGenerator mpg = new AutoGenerator();
//
//        // 全局配置
//        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
//        gc.setOutputDir(projectPath + "/theTest/src/main/java");    // 文件输出路径
//        gc.setOpen(false);   //生成之后是否打开目录
//        gc.setIdType(IdType.NONE);  //主键策略
//        gc.setServiceName("%sService"); //名字设置 %s是占位符，可以理解成类的名字
//        mpg.setGlobalConfig(gc);
//
//        // 数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://localhost:3306/shop_goods?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true");
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("123456");
//        mpg.setDataSource(dsc);
//
//        // 包配置
//        PackageConfig pc = new PackageConfig();
//        pc.setModuleName("mall-goods");
//        pc.setParent("com.highper.performance.code");
//        mpg.setPackageInfo(pc);
//
//        // 策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        strategy.setNaming(NamingStrategy.underline_to_camel);  //驼峰命名
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);    //驼峰命名
//        strategy.setEntityLombokModel(true);    //是否使用Lombok
//        strategy.setRestControllerStyle(true);  //是否生成RestController
//        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");   //公共字段定义
//        strategy.setControllerMappingHyphenStyle(true); //驼峰转连字符
//        strategy.setTablePrefix(pc.getModuleName() + "_");  //表前缀
//        mpg.setStrategy(strategy);
//        mpg.execute();
//    }
//
//}
