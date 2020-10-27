package com.legends.cloud.common.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.legends.cloud.common.utils.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @Author herion
 * @Description //公共请求类
 * @Date  2019/2/26
 * @Param
 * @return
 **/
@Data
@ToString
public class PlatformComReq<T> extends ComReq {

    /**
     * 请求号
     */
    @NotBlank
    @ApiModelProperty(value="随机数加密串" ,required=true,example = "SCL00820190301102012000036",notes = "使用平台方私钥解密，对外报文加密传输时必传")
    private String ranSecret;

    public static class Builder<T> {
        /**
         * 请求号
         */
        private String reqNo;
        @JsonProperty
        private String ranSecret;

        /**
         * 请求时间 （格式：yyyyMMddHHmmss）
         */
        private String reqTime;

        /**
         * 渠道编号
         */
        private String chCode;

        private String version;

        /**
         * 请求数据（JSON格式）
         */
        private T data;

        public Builder ranSecret(String ranSecret) {
            this.ranSecret = ranSecret;
            return this;
        }

        public Builder reqNo(String reqNo) {
            this.reqNo = reqNo;
            return this;
        }

        public Builder reqTime(String reqTime) {
            this.reqTime = reqTime;
            return this;
        }

        public Builder chCode(String chCode) {
            this.chCode = chCode;
            return this;
        }



        public Builder data(T data) {
            this.data = data;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public PlatformComReq<T> build() {
            this.reqTime = DateUtil.getyyyyMMddHHmmssCurDate();
            return new PlatformComReq(this);
        }
    }

    private PlatformComReq(Builder<T> builder) {
        this.ranSecret=builder.ranSecret;
        super.setReqNo(builder.reqNo);
        super.setChCode(builder.chCode);
        super.setReqTime(builder.reqTime);
        super.setVersion(builder.version);
        super.setData(builder.data);
    }
}
