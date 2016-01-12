package org.koumi.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.koumi.annotation.Param;
import org.koumi.annotation.RequestMapping;
import org.koumi.exception.MyException;

public class ControllerUtil {

	/**
	 * 获取控制器中的所有方法（自己定义的方法， 不包括父类的方法）
	 * 
	 * @param objController
	 * @return
	 * @throws Exception
	 */
	public Method[] getControllerMethods(Object objController) throws Exception {
		return ReflectUtil.getObjectMethods(objController);
	}

	/**
	 * 根据方法参数的annotation @Param取得Controller方法中的 参数名称
	 * 
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getControllerMethodParams(Method method)
			throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		Annotation[][] annotations = method.getParameterAnnotations();
		Class<?>[] clazz = method.getParameterTypes();
		for (int i = 0; i < annotations.length; i++) {
			int len = annotations[i].length;
			if (len > 0) {
				for (int j = 0; j < len; j++) {
					Param param = (Param) annotations[i][j];
					params.put(clazz[i].getName() + (i + 1), param.value());
				}
			}
		}
		return params;
	}

	public String getInterceptorClassName() {
		return XMLHelper.getParamAttribute("web-interceptor", "class");
	}

	public Object getInterceptor(String className) throws Exception {
		return Resources.classForName(className).newInstance();
	}

	public Method getControllerRequestMappingMethod(Object objController,String mapping) throws Exception {
		if (objController.getClass().isAnnotationPresent(RequestMapping.class)){
			RequestMapping requestMapping = objController.getClass().getAnnotation(RequestMapping.class);
			String value = requestMapping.value();
			mapping = mapping.replaceFirst(value, "");
		}
		Method[] objectMethods = ReflectUtil.getObjectMethods(objController);
		for (Method method : objectMethods) {
			if (method.isAnnotationPresent(RequestMapping.class)) {
				RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
				String value = requestMapping.value();
				if(value.equals(mapping)){
					return method;
				}
			}
		}
		throw new MyException("找不到映射的方法！");
	}
}
