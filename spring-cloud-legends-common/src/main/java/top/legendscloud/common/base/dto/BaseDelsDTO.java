package top.legendscloud.common.base.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author herion
 * @Date 2020-07-22 16:08
 */
@Data
public class BaseDelsDTO implements Serializable{
    @ApiModelProperty(value = "主键ID")
    @NotNull()
    @Size(min = 1,message = "至少删除一条数据")
    private List<Long> ids;
}