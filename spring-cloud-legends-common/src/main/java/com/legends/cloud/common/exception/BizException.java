
package com.legends.cloud.common.exception;

import com.legends.cloud.common.enums.CommonEnum;
import com.legends.cloud.common.enums.CommonEnumCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BizException extends LegendsException {

	private static final long serialVersionUID = 1L;

	public BizException() {
		super(CommonEnumCode.FAIL);
	}

	public BizException(String code, String msg) {
		super(code,msg);
	}
	
	public BizException(CommonEnum ee) {
		super(ee);
	}
	
	public BizException(CommonEnum ee, Throwable cause) {
		super(ee.getCode(),cause);
	}
}
