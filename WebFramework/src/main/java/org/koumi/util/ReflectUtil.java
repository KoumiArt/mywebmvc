package org.koumi.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtil {
	/**
	 * 获取某个对象中的所有方法（自己定义的方法， 不包括父类的方法）
	 * @param obj
	 * @return  Method数组对象
	 * @throws Exception
	 */
	public static Method[] getObjectMethods(Object obj) throws Exception {
		Method[] methods = obj.getClass().getDeclaredMethods();
		return methods;
	}
	
	/**
	 * 获取某个对象中的所有属性（自己定义的属性， 不包括父类的属性）
	 * @param objController
	 * @return Field数组对象
	 * @throws Exception
	 */
	public static Field[] getObjectFields(Object objController) throws Exception {
		Field[] fields = objController.getClass().getDeclaredFields();
		return fields;
	}
	
	public static PropertyDescriptor[] getBeanInfo(Object obj) throws Exception{
		return Introspector.getBeanInfo(obj.getClass()).getPropertyDescriptors(); 
	}
	/**
	 * 获取某个对象中的某一个方法
	 * @param object
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 * @throws Exception
	 */
	public static Method getObjectMethod(Object object,String methodName,Class<?>... parameterTypes) throws Exception{
		return object.getClass().getDeclaredMethod(methodName,parameterTypes);
	}
}
