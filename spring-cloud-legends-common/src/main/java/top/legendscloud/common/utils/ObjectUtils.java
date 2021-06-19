package top.legendscloud.common.utils;

import org.springframework.cglib.beans.BeanCopier;

public final class ObjectUtils<R, T> {

	public static <T, R> R copy(T source, R target) {

		BeanCopier copy = BeanCopier.create(source.getClass(), target.getClass(), Boolean.FALSE);
		copy.copy(source, target, null);
		return target;
	}

}
