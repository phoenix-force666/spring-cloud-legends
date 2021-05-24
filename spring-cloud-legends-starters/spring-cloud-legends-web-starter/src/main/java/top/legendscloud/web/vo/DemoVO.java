package top.legendscloud.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhanghai on 2019/3/1.
 */
@Data
@ApiModel
public class DemoVO implements Serializable{
    @ApiModelProperty(value="时区时间" ,required=true)
    private String timeZoneOffsetDate;
    @ApiModelProperty(value="本地时间" ,required=true)
    private String localDate;
}