package com.legends.cloud.common.base;

import com.legends.cloud.common.utils.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author herion
 * @Description //公共请求类
 * @Date  2019/2/26
 * @Param
 * @return
 **/
@Data
@ToString
public class ComReq<T> implements Serializable {

    private static final long serialVersionUID = 3259191238877761665L;

    /**
     * 请求号
     */
    @NotBlank
    @ApiModelProperty(value="请求流水号" ,required=true,example = "SCL00820190301102012000036",notes = "唯一不重复")
    private String reqNo;

    /**
     * 请求时间 （格式：yyyyMMddHHmmss）
     */
    @NotBlank
    @ApiModelProperty(value="请求时间" ,required=true,example = "20190301102156",notes = "请求时间 （格式：yyyyMMddHHmmss）")
    private String reqTime;

    /**
     * 渠道编号
     */
    @NotBlank
    @ApiModelProperty(value="渠道编号" ,required=true,example = "jfapl008",notes = "渠道编号系统分配")
    private String chCode;

    @NotBlank
    @ApiModelProperty(value="版本号" ,required=true,example = "1.0",notes = "接口版本号")
    private String version;

    /**
     * 请求数据（JSON格式）
     */
    @Valid
    @ApiModelProperty(value="业务数据" ,required=true,notes = "业务报文json格式")
    private T data;

    public ComReq() {

    }

    public static class Builder<T> {
        /**
         * 请求号
         */
        private String reqNo;

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
        public ComReq<T> build() {
            this.reqTime = DateUtil.getyyyyMMddHHmmssCurDate();
            return new ComReq(this);
        }
    }

    private ComReq(Builder<T> builder) {
        this.reqNo = builder.reqNo;
        this.reqTime = builder.reqTime;
        this.chCode = builder.chCode;
        this.version = builder.version;
        this.data=builder.data;
    }
}
