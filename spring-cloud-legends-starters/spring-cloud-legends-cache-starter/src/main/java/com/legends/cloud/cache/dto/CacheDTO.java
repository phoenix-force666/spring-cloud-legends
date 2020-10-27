package com.legends.cloud.cache.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by zhanghai on 2018/7/27.
 */
@Data
public class CacheDTO implements Serializable{
    private static final long serialVersionUID = -5542104968713313606L;
    @NotBlank(message = "aplicationName 不能为空")
    @ApiModelProperty(name="aplicationName",value="应用名",example = "LEGENDS",required=true)
    private String aplicationName;


    @ApiModelProperty(name="cacheType",value="缓存类型",example = "0" ,allowableValues = "0:gauva,1:redis,2:all",required=true)
    @NotBlank(message = "缓存类型不能为空")
    private String cacheType;

    @ApiModelProperty(name="cacheName",value="cacheName",example = "cacheName",required=false)
    private String cacheName;

    @ApiModelProperty(name="cacheKey",value="缓存key",example = "cacheKey",required=true)
    @NotBlank(message = "缓存key不能为空")
    private String cacheKey;

    @NotBlank(message = "操作用户不能为空")
    @ApiModelProperty(name="userName",value="操作用户",required=true)
    private String userName;

    @ApiModelProperty(name="checkCode",value="验证码",required=true)
    private String checkCode;
}
