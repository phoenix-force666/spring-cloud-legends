package top.legendscloud.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by zhanghai on 2019/3/1.
 */
@Data
@ApiModel
public class DemoDTO implements Serializable{
    @ApiModelProperty(value="时区" ,required=true,example = "8",notes = "时区描述")
    private float timeZoneOffset;


    @NotBlank
    @ApiModelProperty(value="type" ,required=true,example = "8",notes = "type")
    private String type;
}
