package top.legendscloud.common.base.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description
 * @Author herion
 * @Date 2020-07-22 16:06
 */
@Data
public class BaseDelDTO implements Serializable{
    @ApiModelProperty(value = "主键ID")
    @Min(value = 1,message = "id不能为空")
    @NotNull
    private Long id;
}