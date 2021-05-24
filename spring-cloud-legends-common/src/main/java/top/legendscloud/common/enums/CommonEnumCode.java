package top.legendscloud.common.enums;

import lombok.Getter;

/**
 * @Author herion
 * @Description 系统基础业务错误码范围：00 ～ 99；错误码前缀为系统简称。
 * @Date  2019/2/26
 * @Param
 * @return
 **/
public enum CommonEnumCode implements CommonEnum {

    SUCCEED("0000", "成功"),
    UNKNOWN_ERR("UE", "未知异常"),
    SYS_ERR("SE", "系统异常"),

    //P 参数相关
    MESSAGE_FORMAT_ERR("P0", "报文格式错误"),
    INVALID_REQUIRED_PARAMETERS("P1", "必填参数无效"),
    INVALID_REQUIRED_PARAMETERS_BLANK("PN", "必填参数为空或无效"),
    INVALID_PARAMETER("PI", "参数非法"),

    //A 安全相关
    INVALID_PERMISSIONS("A0", "无效的权限"),
    SIGNATURE_ERR ("AE", "签名验证失败"),
    TOEKN_ERR("AT", "TOKEN验证失败"),
    TOEKN_MISMATCH("A1", "token不匹配"),
    TOEKN_EXPIRED("A2", "token已失效"),
    SAFETY_CHECK_ABNORMALITY("A9", "安全校验异常"),
    ILLEGAL_REQUEST("AI", "非法请求"),
    SYS_FUNCTION_CLOSE("A3", "接口关闭"),
    ENC_DEC_FAIL("A4", "加解密失败"),
    ACCESS_DENIED("A5", "拒绝访问"),

    //远程调用
    SYS_OUTTIME("R1", "下游系统响应超时"),
    SYS_UNTOUCHABLE("R2", "下游系统找不到或无法达到"),
    REMOTE_CALL_EXCEPTION("R9", "远程调用异常"),

    //服务端
    RESOURCE_NOTFOUND("S4", "访问资源不存在"),

    FAIL("99", "失败"),
    SYS_BUSY("90", "系统繁忙"),

    FREQUENT_OPERATION("62", "操作过于频繁"),

    SYS_DB_COLLECTION_ERR("93", "数据库连接错误"),
    SYS_DB_OPERATION_ERR("94", "数据库操作异常");
    
    /**
     * 代码
     */
    @Getter
    private final String code;

    /**
     * 说明
     */
    @Getter
    private final String msg;

    CommonEnumCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * @param code
     * @return ErrorEnum 返回类型
     * @Title: getEnumByCode
     */
    public static CommonEnumCode getEnumByCode(String code) {

        for (CommonEnumCode item : CommonEnumCode.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }

        return CommonEnumCode.UNKNOWN_ERR;
    }
}
