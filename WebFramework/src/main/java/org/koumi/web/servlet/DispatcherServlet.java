package org.koumi.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.koumi.context.listener.ContextLoader;
import org.koumi.exception.MyException;
import org.koumi.util.ControllerUtil;
import org.koumi.util.MyUtil;
import org.koumi.util.XMLHelper;
import org.koumi.web.resolve.FormResolveManager;
import org.koumi.web.resolve.ModelResoleManager;

public class DispatcherServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ControllerUtil controllerUtil;
	ModelResoleManager modelResoleManager;
	private String configLocation ;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		configLocation = config.getServletContext().getInitParameter("configLocation");
		configLocation = configLocation.startsWith("\\")?configLocation : "\\" + configLocation;
		XMLHelper.configLocation = config.getServletContext().getRealPath("")+configLocation.trim();
		if (controllerUtil == null)
			controllerUtil = new ControllerUtil();
		if(modelResoleManager == null)
			modelResoleManager = new ModelResoleManager();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ȡuri
		try {
			String uris = request.getRequestURI();
			uris = uris.replaceFirst(request.getContextPath(), "");
			//ȡ��bean��Ҫִ�е�method
			Object[] beanAndMethodMapping = ContextLoader.getRestFul(uris);
			String methodMapping = beanAndMethodMapping[0].toString();
			Object bean = beanAndMethodMapping[1];
			//ִ��
			doAction(request, response,bean,methodMapping);
		} catch (MyException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param request
	 * @param response
	 * @param controllerSimplName
	 * @param method
	 * @throws Exception 
	 */
	private void doAction(HttpServletRequest request,
			HttpServletResponse response,Object bean,String methodMapping) throws Exception {
		Method method = controllerUtil.getControllerRequestMappingMethod(bean,methodMapping);
		// ��ȡ Anntation����
		Map<String, String> params = controllerUtil.getControllerMethodParams(method);
		// ��ȡ�����Ĳ��������б�
		Class<?>[] clazz = method.getParameterTypes();
		// ���������б�
		Object[] methodParams = new Object[clazz.length];
		for (int j = 0; j < clazz.length; j++) {
			String inputName = params.get(clazz[j].getName() + (j + 1));							
			methodParams[j] = setParam(request, clazz[j], inputName);
		}
		method.setAccessible(true);
		Object result = method.invoke(bean,methodParams);
		// ģ�ͽ�������
		modelResoleManager.setRequest(request);
		modelResoleManager.setResponse(response);
		modelResoleManager.execModelResolve(result,method);
		return;
	}

	/**
	 * ���ò���
	 * @param request
	 * @param clazz
	 * @param inputName
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	private Object setParam(HttpServletRequest request, Class<?> clazz,  String inputName)
			throws InstantiationException, IllegalAccessException, Exception {
		if (clazz.isPrimitive() || clazz.isInstance("java.lang.String")) {
			// �򵥲�����װ��ȥrequest����
			String paramValue = inputName == null? null : request.getParameter(inputName);
			// �����ڴ˴���װbean����
			if (paramValue == null){
				throw new NullPointerException(inputName
						+ " is NULL!(����form����)");
			}
			return paramValue;
		}							
		//����ķ�װ
		Object bean  = MyUtil.getInstance(clazz);
		FormResolveManager formResolveManager = new FormResolveManager();
		formResolveManager.setRequest(request);
		bean = formResolveManager.getBean(inputName,bean);
		return bean; 

	}
}
