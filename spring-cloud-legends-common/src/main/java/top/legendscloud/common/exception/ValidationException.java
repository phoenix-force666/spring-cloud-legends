package top.legendscloud.common.exception;


import top.legendscloud.common.enums.CommonEnum;
import top.legendscloud.common.enums.CommonEnumCode;

public class ValidationException extends LegendsException {

    private static final long serialVersionUID = 8130949818882836326L;

    public ValidationException() {
        super(CommonEnumCode.INVALID_REQUIRED_PARAMETERS_BLANK);
    }

    public ValidationException(CommonEnum ee) {
        super(ee.getCode(), ee.getMsg());
    }

    public ValidationException(final String code, final String message) {
        super(code, message);
    }

    public ValidationException(final String code, final Throwable throwable) {
        super(code, throwable);
    }

}
