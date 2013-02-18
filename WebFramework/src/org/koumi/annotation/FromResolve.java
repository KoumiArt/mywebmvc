package org.koumi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
//������ʱ���ص�Annotation��JVM��
public @interface FromResolve {
	/**
	 * ���ڽ�����Java���ͣ��磺java.util.List (���������Ĳ���������ΪList);�����������Ϊ�������͵����飨�磺List[],��Ӧ��Ϊ��java.util.List��
	 * @return
	 */
	String value();
}
