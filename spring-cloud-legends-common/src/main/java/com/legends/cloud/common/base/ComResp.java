package com.legends.cloud.common.base;

import com.legends.cloud.common.enums.CommonEnumCode;
import com.legends.cloud.common.utils.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Author herion
 * @Description //公共响应类
 * @Date  2019/2/26
 * @Param 
 * @return 
 **/
@Data
@ToString
public class ComResp<T> {
    private static final long serialVersionUID = 7463942277544032844L;

    @ApiModelProperty(value="响应流水号" ,required=true,example = "SCL00820190301102012000036",notes = "与请求流水号reqNo保持一致")
    private String respNo;

    @ApiModelProperty(value="响应时间" ,required=true,example = "20190301102156",notes = "响应时间 （格式：yyyyMMddHHmmss）")
    private String respTime;
    @ApiModelProperty(value="渠道编号" ,required=true,example = "SCL008",notes = "渠道编号系统分配")
    private String chCode;
    @ApiModelProperty(value="响应码" ,required=true,example = "0000",notes = "0000表示成功")
    private String code;
    @ApiModelProperty(value="响应信息描述" ,required=true,example = "成功",notes = "响应信息描述")
    private String msg;
    @ApiModelProperty(value="业务数据" ,required=true,notes = "业务报文json格式")
    private T data;


    public static class Builder<T> {

        private String respNo;
        private String respTime;
        private String chCode;

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

        public ComResp<T> build() {
            this.respTime = DateUtil.getyyyyMMddHHmmssCurDate();
            return new ComResp(this);
        }

    }

    public ComResp(){}
    private ComResp(Builder<T> builder) {
        this.respNo = builder.respNo;
        this.respTime = builder.respTime;
        this.chCode = builder.chCode;
        this.code=builder.code;
        this.msg=builder.msg;
        this.data=builder.data;
    }

}
