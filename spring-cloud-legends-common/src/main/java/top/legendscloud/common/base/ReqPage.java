package top.legendscloud.common.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description
 * @Author herion
 * @Date 2020-07-15 16:58
 */
@Data
@ToString
public class ReqPage<T> implements Serializable {
    @NotNull
    @ApiModelProperty(value="页面" ,required=true,example = "1",notes = "页面")
    private long page=1L;

    @NotNull
    @ApiModelProperty(value="页面数据量" ,required=true,example = "10",notes = "页面数据量")
    private long size=10L;

    @ApiModelProperty(value="是否正序排列，默认 true" ,required=true,example = "true",notes = "排序方式排序[true:正序; false:倒序]")
    private boolean sort=true;
    @ApiModelProperty(value="排序字段" ,required=true,notes = "排序字段,参照返回字段,此字段对应的是数据库列名")
    private String sortName;

    /**
     * 请求数据（JSON格式）
     */
    @Valid
    @ApiModelProperty(value="业务数据" ,required=true,notes = "业务报文json格式")
    private T data;

}