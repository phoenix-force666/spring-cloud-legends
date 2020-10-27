
package com.legends.cloud.common.enums;

import lombok.Getter;

/**
 * @Description: 是否删除枚举
 * @author herion 2017年4月19日
 *
 */
public enum IsDeleteEnum {

    TRUE(1, "是(删除)"),
    FALSE(0, "否(未删除)");

    @Getter
    private int type;
    
    @Getter
    private String desc;

    IsDeleteEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
