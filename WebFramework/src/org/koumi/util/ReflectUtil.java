package org.koumi.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtil {
	/**
	 * ��ȡĳ�������е����з������Լ�����ķ����� ����������ķ�����
	 * @param obj
	 * @return  Method�������
	 * @throws Exception
	 */
	public static Method[] getObjectMethods(Object obj) throws Exception {
		Method[] methods = obj.getClass().getDeclaredMethods();
		return methods;
	}
	
	/**
	 * ��ȡĳ�������е��������ԣ��Լ���������ԣ� ��������������ԣ�
	 * @param objController
	 * @return Field�������
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
	 * ��ȡĳ�������е�ĳһ������
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
