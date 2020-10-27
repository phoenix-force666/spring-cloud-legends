package com.legends.cloud.cache.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhanghai on 2018/8/7.
 */
@Data
public class GuavaCacheDTO implements Serializable{
    private static final long serialVersionUID = 3188937933580127223L;
    private String cacheName;
}
