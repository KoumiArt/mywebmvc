package org.koumi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
//�����ֶΣ�����������
@Retention(RetentionPolicy.RUNTIME)
//������ʱ���ص�Annotation��JVM��
public @interface Param {
	String value();
}
