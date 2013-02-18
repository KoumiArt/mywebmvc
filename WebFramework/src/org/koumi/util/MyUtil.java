package org.koumi.util;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class MyUtil {
	private static String DEFAULT_FORMAT = "yyyy-mm-dd";
	
	public static Date stringToDate(String dateStr) {
		return stringToDate(dateStr, DEFAULT_FORMAT);
	}
	public static Date stringToDate(String dateStr, String formatStr) {
		Date date = null;
		try {
			DateFormat dd = new SimpleDateFormat(formatStr);
			date = dd.parse(dateStr);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String dateToString(Date date){
		return dateToString(date, DEFAULT_FORMAT);
	}
	public static String dateToString(Date date,String formatStr){
		String dateStr = null;
		if(date !=null && formatStr !=null){
			SimpleDateFormat dateFormate = new SimpleDateFormat(formatStr);
			dateStr = dateFormate.format(date);
		}
		return dateStr;
	}
	
	/**
	 * Ê××ÖÄ¸±ßÐ¡Ð´
	 * @param str
	 * @return
	 */
	public static String firstToUpperCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	public static int getArrayLengths() {
		// TODO Auto-generated method stub
		return 5;
	}
	
	/**
	 * @param clazz
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Object getObjectParamInstance(Class<?> clazz) throws InstantiationException, IllegalAccessException{
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
