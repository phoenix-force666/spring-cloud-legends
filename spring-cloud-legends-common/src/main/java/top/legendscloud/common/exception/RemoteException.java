
package top.legendscloud.common.exception;

import top.legendscloud.common.enums.CommonEnum;
import top.legendscloud.common.enums.CommonEnumCode;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RemoteException extends LegendsException {

	private static final long serialVersionUID = 1L;

	public RemoteException() {
		super(CommonEnumCode.REMOTE_CALL_EXCEPTION);
	}

	public RemoteException(String code, String message) {
		super(code,message);

	}

	public RemoteException(CommonEnum ee) {
		super(ee.getCode(), ee.getMsg());
	}

	public RemoteException(CommonEnum ee, Throwable cause) {
		super(ee.getCode(), cause);
	}
	
}
