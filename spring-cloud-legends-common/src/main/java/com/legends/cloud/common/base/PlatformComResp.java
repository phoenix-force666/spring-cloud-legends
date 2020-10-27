package com.legends.cloud.common.base;

import com.legends.cloud.common.enums.CommonEnumCode;
import com.legends.cloud.common.utils.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @Author herion
 * @Description //公共响应类
 * @Date  2019/2/26
 * @Param 
 * @return 
 **/
@Data
@ToString
public class PlatformComResp<T> extends ComResp {
    private static final long serialVersionUID = 7463942277544032844L;

    @NotNull
    @ApiModelProperty(value="随机数加密串" ,required=true,example = "SCL00820190301102012000036",notes = "使用平台方私钥解密，对外报文加密传输时必传")
    private String ranSecret;

    public static class Builder<T> {

        private String respNo;
        private String respTime;
        private String chCode;
        private String ranSecret;
        private String code;
        private String msg;
        private T data;


        public Builder respNo(String respNo) {
            this.respNo = respNo;
            return this;
        }

        public Builder respTime(String respTime) {
            this.respTime = respTime;
            return this;
        }

        public Builder chCode(String chCode) {
            this.chCode = chCode;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder data(T data) {
            this.data = data;
            return this;
        }

        public Builder ranSecret(String ranSecret) {
            this.ranSecret = ranSecret;
            return this;
        }

        public Builder error(CommonEnumCode commonEnum) {
            this.code = commonEnum.getCode();
            this.msg = commonEnum.getMsg();
            return this;
        }

        public Builder fromResp(String chCode,String respNo) {
            this.chCode = chCode;
            this.respNo = respNo;
            return this;
        }

        public Builder fromReq(ComReq comReq) {
            this.respNo = comReq.getReqNo();
            this.chCode=comReq.getChCode();
            return this;
        }

        public Builder fail() {
            this.error(CommonEnumCode.FAIL);
            return this;
        }

        public Builder success() {
            this.error(CommonEnumCode.SUCCEED);
            return this;
        }

        public PlatformComResp<T> build() {
            this.respTime = DateUtil.getyyyyMMddHHmmssCurDate();
            return new PlatformComResp(this);
        }

    }

    public PlatformComResp(){}
    private PlatformComResp(Builder<T> builder) {
        this.setRespNo(builder.respNo);
        this.setRespTime(builder.respTime);
        this.setChCode(builder.chCode);
        this.setCode(builder.code);
        this.setMsg(builder.msg);
        this.setData(builder.data);
        this.ranSecret=builder.ranSecret;
    }

}
