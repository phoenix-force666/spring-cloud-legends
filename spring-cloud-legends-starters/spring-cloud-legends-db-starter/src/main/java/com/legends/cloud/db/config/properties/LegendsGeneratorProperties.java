package com.legends.cloud.db.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "legends.generator")
@Data
public class LegendsGeneratorProperties {
    //父包路径
    private String  parentPackagePath;
    //项目路径
    private String projectPath = System.getProperty("user.dir");
    // 项目模块名
    private String projectModule;
    //包模块名
    private String packageModule;
    //数据库类型
    private String dbType;
    //表表名
    private String tables;
}
