package org.koumi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
//在运行时加载到Annotation到JVM中
public @interface Controller {
	String value() default "";
}
