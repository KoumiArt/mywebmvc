package org.koumi.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.koumi.exception.MyException;

public class ModelUtil {
	private HttpServletRequest request;
	
	/**
	 * 封装from表单的属性为bean
	 * @param obj 要封装的对象
	 * @param inputName from表单中的名称
	 * @return
	 */
	public Object getFromBean(Object obj,String inputName) throws Exception{
		try {			
			PropertyDescriptor pd[] = ReflectUtil.getBeanInfo(obj); 
			if(pd == null || pd.length <= 0)
				return null;
			for (PropertyDescriptor propertyDescriptor : pd) {
				Method setter = propertyDescriptor.getWriteMethod(); 
				if(setter != null) {
					String fieldName = propertyDescriptor.getName();
					String reqParamName = inputName == null?fieldName:inputName + "." + fieldName;
					Object inputValue = request.getParameter(reqParamName);
					Object param = null;
					if(propertyDescriptor.getPropertyType().isPrimitive()){
						param = 0;
					} else {
						if(inputValue == null || inputValue.toString().trim().length() <=0 ){
							param = propertyDescriptor.getPropertyType().newInstance();
						} else {
							param = inputValue;
						}
					}
					setter.setAccessible(true);
					setter.invoke(obj,param);
				}
			}				
		} catch (Exception e) {
			throw new MyException(obj.getClass().getName()+":解析错误，请检查控制器对应的方法的参数，是否有相应的解析器。");
		}
		return obj;
	}
	
	public void setRequest(HttpServletRequest request){
		this.request = request;
	}
}
