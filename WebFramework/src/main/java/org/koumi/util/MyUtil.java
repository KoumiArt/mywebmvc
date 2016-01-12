package org.koumi.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MyUtil {
	
	/**
	 * 首字母边小写
	 * @param str
	 * @return
	 */
	public static String firstToUpperCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	public static int getArrayLengths() {
		return 5;
	}
	
	/**
	 * @param clazz
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Object getInstance(Class<?> clazz) throws InstantiationException, IllegalAccessException{
		Object bean  = null;
		if(clazz.isInterface() && clazz.getName().equals("java.util.List")){
			bean = new ArrayList<Object>();
		} 
		if(clazz.isInterface() &&  clazz.getName().equals("java.util.Map")){
			bean = new HashMap<Object,Object>();
		} 
		if(clazz.isInterface() &&  clazz.getName().equals("java.util.set")){
			bean = new HashSet<Object>();
		} 
		if(clazz.isArray()){
			bean = Array.newInstance(clazz.getComponentType(), MyUtil.getArrayLengths());
		}
		if(!clazz.isInterface() && !clazz.isArray()){
			bean = clazz.newInstance();
		}
		return bean;
	}
}
