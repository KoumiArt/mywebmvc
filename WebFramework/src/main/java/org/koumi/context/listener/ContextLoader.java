package org.koumi.context.listener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.koumi.annotation.RequestMapping;
import org.koumi.exception.MyException;
import org.koumi.util.ComponentScan;
import org.koumi.util.Resources;
import org.koumi.util.XMLHelper;

public class ContextLoader {

	/**
	 * 用户存放bean
	 */
	private final static Map<String, Object> sigletons = new HashMap<String, Object>();
	/**
	 * Map<class_requestMapping,Map<method_requestMapping,bean>
	 */
	private final static Map<String,Object> restFulMapping = new HashMap<String, Object>();

	/**
	 * 1.扫描package 2.取得每个类的@Controller,放入restFulMapping中
	 * 
	 * @throws MyException
	 */
	public void load() throws MyException {
		initXMLBean();
		initAnnotationBean();
	}

	private void initAnnotationBean() throws MyException {
		String controllerPackage = XMLHelper.getParamAttribute(
				"controller-component-scan", "package");
		if (controllerPackage != null && controllerPackage.trim().length() > 0) {
			Set<Class<?>> classes = ComponentScan.getClasses(controllerPackage);
			for (Class<?> controllerClass : classes) {
				if (isAnnotationPresent(controllerClass, RequestMapping.class)) {
					setRestFulMapping(controllerClass);
				}
			}
		}
	}

	private void setRestFulMapping(Class<?> controllerClass) throws MyException {
		RequestMapping requestMapping = controllerClass
				.getAnnotation(RequestMapping.class);
		String value = "";
		if (requestMapping != null) {
			value = requestMapping.value();	
		} 
		setRequestMapping(controllerClass,value);		
	}

	/**
	 * 填充class中的有注解（RequestMapping）为一个Map<methodMapping,bean>
	 * @param controllerClass
	 * @throws MyException
	 */
	private void setRequestMapping(Class<?> controllerClass,String classMapping)
			throws MyException {
		Method[] declaredMethods = controllerClass.getDeclaredMethods();
		if (declaredMethods != null) {
			for (Method method : declaredMethods) {
				if (method.isAnnotationPresent(RequestMapping.class)) {
					RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
					String value = requestMapping.value();
					String beanClassName = controllerClass.getName();
					try {
						String key = classMapping + value;
						if(restFulMapping.containsKey(key))
							throw new MyException(beanClassName + "已存在的映射：" + "key");
						restFulMapping.put(key, controllerClass.newInstance());
					} catch (InstantiationException e) {
						throw new MyException("初始化类：\"" + beanClassName + "\"失败！");
					} catch (IllegalAccessException e) {
						throw new MyException("初始化类：\"" + beanClassName + "\"失败！");
					}
				}
			}
		}
	}

	private void initXMLBean() throws MyException {
		Map<String, String> nodesAttributes = XMLHelper.getNodesAttributes(
				"bean", "id", "class");
		if (nodesAttributes != null && nodesAttributes.size() > 0) {
			Set<Entry<String, String>> set = nodesAttributes.entrySet();
			for (Entry<String, String> entry : set) {
				String value = entry.getValue();
				String[] values = value.split(",");
				String beanName = values[0];
				String beanClassName = values[1];
				Class<?> beanClass;
				try {
					beanClass = Resources.classForName(beanClassName);
					sigletons.put(beanName, beanClass.newInstance());
				} catch (ClassNotFoundException e) {
					throw new MyException("配置文件中，配置的额<bean id=\"" + beanName
							+ "\" class=\"" + beanClassName + "\" />不存在的class");
				} catch (InstantiationException e) {
					throw new MyException("初始化类：\"" + beanClassName + "\"失败！");
				} catch (IllegalAccessException e) {
					throw new MyException("初始化类：\"" + beanClassName + "\"失败！");
				}
			}
		}
	}

	/**
	 * 根据beanName取得Bean对象
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		return sigletons.get(beanName);
	}
	
	public static void main(String[] args) {
		String url = "/user/login?";
		url = url.contains(".")?url.substring(0,url.indexOf(".")):url;
		System.out.println(url);
		url = url.contains("?")?url.substring(0,url.indexOf("?")):url;
		System.out.println(url);
	}
	
	public static Object[] getRestFul(String url) throws MyException {
		String key = "";
		Object[] obj = new Object[2];
		key = url.contains(".")?url.substring(0,url.indexOf(".")):url;
		key = key.contains("?")?key.substring(0,key.indexOf("?")):key;
		Object bean = restFulMapping.get(key);
		if (bean == null) {
			throw new MyException("不存在的URL映射:"+key);
		}
		obj[0] = key;
		obj[1] = bean;
		return  obj;
	}

	/**
	 * 
	 * @param clazz要判断的class
	 * @param annotationClass
	 *            注解class
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean isAnnotationPresent(Class clazz, Class annotationClass) {
		if (clazz.isAnnotationPresent(annotationClass)) {
			return true;
		}
		Method[] declaredMethods = clazz.getDeclaredMethods();
		if (declaredMethods != null) {
			for (Method method : declaredMethods) {
				if (method.isAnnotationPresent(annotationClass)) {
					return true;
				}
			}
		}
		return false;
	}
}
