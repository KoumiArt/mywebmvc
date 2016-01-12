package org.koumi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
//在运行时加载到Annotation到JVM中
public @interface FromResolve {
	/**
	 * 用于解析的Java类型，如：java.util.List (解析方法的参数的类型为List);如果是数组则为具体类型的数组（如：List[],则应该为：java.util.List）
	 * @return
	 */
	String value();
}
