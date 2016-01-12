package org.koumi.web.resolve;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.koumi.annotation.FromResolve;
import org.koumi.exception.MyException;
import org.koumi.util.ComponentScan;
import org.koumi.util.ModelUtil;
import org.koumi.util.MyUtil;
import org.koumi.util.Resources;
import org.koumi.util.XMLHelper;

public class FormResolveManager {
	private HttpServletRequest request;

	/**
	 * 执行form解析
	 * 
	 * @param result
	 * @return
	 * @throws Exception
	 */
	public Object execFormResolve(Object result, String inputName) throws Exception {
		// 读取XML解析
		Map<String, String> resolveMap = XMLHelper.getChildNodeAttributes("web-from-resolve", "class", "type");
		if (resolveMap != null) {
			Set<Entry<String, String>> set = resolveMap.entrySet();
			for (Entry<String, String> entry : set) {
				String value = entry.getValue();
				String[] values = value.split(",");
				String classProperty = values[0];
				String typeProperty = values[1];
				Class<?> clazz = Resources.classForName(typeProperty);
				if (clazz.isInstance(result)) {
					return getResolve(inputName, classProperty);
				}
			}
		}
		// 读取annotation进行解析
		String fromResolvePackage = XMLHelper.getParamAttribute("from-resolve-component-scan", "package");
		if (fromResolvePackage != null && fromResolvePackage.trim().length() > 0) {
			Set<Class<?>> classes = ComponentScan.getClasses(fromResolvePackage);
			for (Class<?> fromResolveClass : classes) {
				FromResolve fromResolveAnnontaion = fromResolveClass.getAnnotation(FromResolve.class);
				if (fromResolveAnnontaion != null) {
					// 获取Bean的名称，如果没有设Bean的名称默认为类名首字母小写
					String classProperty = fromResolveClass.getName();
					String typeProperty = fromResolveAnnontaion.value();
					Class<?> clazz = Resources.classForName(typeProperty);
					Object obj = result;
					if (result.getClass().isArray()) {
						obj = MyUtil.getInstance(clazz);
					}
					if (clazz.isInstance(obj)) {
						return getResolve(inputName, classProperty);
					}
					obj = null;
				}
			}
		}
		if (result.getClass().isArray()) {
			throw new MyException(result.getClass().getName() + " : 没对应的解析器！");
		}
		// 找不到解析器直接封装bean
		ModelUtil modelUtil = new ModelUtil();
		modelUtil.setRequest(request);
		return modelUtil.getFromBean(result, inputName);
	}

	/**
	 * @param inputName
	 *            Anntation参数的value
	 * @param obj
	 *            Anntation参数的类型实例
	 * @return
	 * @throws Exception
	 */
	public Object getBean(String inputName, Object obj) throws Exception {
		return execFormResolve(obj, inputName);
	}

	/**
	 * @param inputValue
	 * @param resolveClassName
	 * @return
	 * @throws Exception
	 */
	private Object getResolve(String inputName, String resolveClassName) throws Exception {
		if (resolveClassName == null || resolveClassName.trim().length() <= 0) {
			return null;
		}
		Class<?> clazz = Resources.classForName(resolveClassName);
		IResolve resovle = (IResolve) clazz.newInstance();
		resovle.setRequest(request);
		return resovle.resolve(inputName);
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}