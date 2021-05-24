package top.legendscloud.common.exception;

import top.legendscloud.common.enums.CommonEnum;
import top.legendscloud.common.utils.StringUtil;

public class LegendsException extends RuntimeException {

    private static final long serialVersionUID = -8143229915907803343L;
    private CommonEnum commonEnum;

    private String code;
    private String msg;

    public LegendsException(CommonEnum commonEnum) {
        super();
        this.commonEnum = commonEnum;
        this.code = commonEnum.getCode();
        this.msg = commonEnum.getMsg();
    }

    public LegendsException(final String code) {
        initException(code);
    }

    public LegendsException(final String code, final String msg) {

        super(msg);
        this.code = code;
        this.msg = msg;
        initException(code);
    }

    public LegendsException(final String code, final Throwable throwable) {
        super(throwable);
        initException(code);
        this.code = code;
        this.msg = throwable.getMessage();
    }

    public String getCode() {
        final Throwable cause = this.getCause();
        if (cause != null && (cause instanceof LegendsException)) {
            return ((LegendsException) cause).getCode();
        }
        return this.code;
    }

    public String getMsg(){
        return StringUtil.isNullOrEmpty(this.getMessage())?this.msg:this.getMessage();
    }

    private void initException(final String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.getCode();
    }
}
