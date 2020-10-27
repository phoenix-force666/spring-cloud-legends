package com.legends.cloud.common.exception;


import com.legends.cloud.common.enums.CommonEnum;
import com.legends.cloud.common.enums.CommonEnumCode;

/**
 * @author herion
 * @version V1.0
 * @Title: legends-common
 * @Description: 安全类异常
 * @date 2017/9/21 11:34
 */
public class AuthException extends LegendsException {

        private static final long serialVersionUID = 8130949818882836326L;

        public AuthException() {
            super(CommonEnumCode.SAFETY_CHECK_ABNORMALITY.getCode(), CommonEnumCode.SAFETY_CHECK_ABNORMALITY.getMsg());
        }

        public AuthException(CommonEnum ee) {
            super(ee.getCode(), ee.getMsg());
        }

        public AuthException(final String code, final String message) {
            super(code, message);
        }

        public AuthException(final String code, final Throwable throwable) {
            super(code, throwable);
        }

    }
