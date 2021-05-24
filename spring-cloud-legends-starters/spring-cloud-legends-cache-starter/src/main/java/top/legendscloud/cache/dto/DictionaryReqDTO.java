package top.legendscloud.cache.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class DictionaryReqDTO {
    
    @NotEmpty(message = "分组号不能为空")
    @ApiModelProperty(name="groupCode",value="分组号",required=true)
    private String groupCode;

    public DictionaryReqDTO(String groupCode){
        this.groupCode = groupCode;
    }
}
