package top.legendscloud.cache.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by zhanghai on 2018/8/1.
 */
@Data
public class CachePutDTO extends CacheDTO{
    @NotBlank(message = "cacheValue不能为空")
    @ApiModelProperty(name="cacheValue",value="cacheValue",example = "cacheValue",required=true)
    private String cacheValue;
}
