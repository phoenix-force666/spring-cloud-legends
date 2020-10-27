package com.legends.cloud.db.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by zhanghai on 2019/5/14.
 */
@ConfigurationProperties(prefix = "legends.db") //获取属性值
public class LegendsDBProperties {

    private static final String TYPE = "oracle";
    private static final String MAPPER_SCAN = "com.legends.cloud";

    private String type = TYPE;
    private String mapperScan = MAPPER_SCAN;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMapperScan() {
        return mapperScan;
    }

    public void setMapperScan(String mapperScan) {
        this.mapperScan = mapperScan;
    }
}