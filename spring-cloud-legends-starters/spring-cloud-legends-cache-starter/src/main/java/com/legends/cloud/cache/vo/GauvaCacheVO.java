package com.legends.cloud.cache.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by zhanghai on 2018/8/1.
 */
@Data
public class GauvaCacheVO implements Serializable{
    @ApiModelProperty(name="map",value="缓存map",required=true)
    private Map<String, Object> map;

    @ApiModelProperty(name="cacheName",value="cacheName",required=true)
    private String cacheName;

    public GauvaCacheVO(){

    }

    public GauvaCacheVO(Map<String, Object> map,String cacheName){
        this.map=map;
        this.cacheName=cacheName;
    }
}
