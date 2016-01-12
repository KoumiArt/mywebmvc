package org.koumi.web.resolve;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.koumi.annotation.ResponseBody;
import org.koumi.util.JsonUtil;
import org.koumi.util.Resources;
import org.koumi.util.XMLHelper;
import org.koumi.web.model.ModelMap;

public class ModelResoleManager {
	private HttpServletRequest request;
	private HttpServletResponse response;

	public void execModelResolve(Object result, Method method) throws Exception {	
		//JSON解析
		if(method.isAnnotationPresent(ResponseBody.class)){
			//向客户端写入JSONBean
			response.setContentType("text/json,charset=utf-8");
			response.setCharacterEncoding("gbk");
			response.getWriter().println(JsonUtil.toJson(result));
		} else {
			// 模型解析方法 
			if (result instanceof String) {
				forward(result.toString());
				return ;
			} else if (result instanceof ModelMap) {
				ModelMap modelMap = (ModelMap) result;
				putRequestAttribute(modelMap);
				putSessionAttribute(modelMap);
				if (modelMap.getResult().startsWith("redirect:")) {
					redirect(modelMap.getResult().replaceFirst("redirect:", ""));
					return; 
				}
				forward(modelMap.getResult());
				return;
			} else {
				
				//读取web-model-resolve
				Map<String,String> resolveMap = XMLHelper.getChildNodeAttributes("web-model-resolve", "class");
				IModelResolve resoleObj  = null;			
				if(null != resolveMap && resolveMap.size() >0) {
					Set<Entry<String, String>> set = resolveMap.entrySet();
					for (Entry<String, String> entry : set) {
						try {
							resoleObj = (IModelResolve) Resources.classForName(entry.getValue()).newInstance();
							if(resoleObj == null)
								resoleObj = new DefaultModelResolve();
							resoleObj.modelResolve(result);
							if(resoleObj.getIsResolve()) return ;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					resoleObj = new DefaultModelResolve();
					if(resoleObj.getIsResolve()) return ;
				}
			}
		}
	}

	
	private void redirect(String result) throws IOException {
		response.sendRedirect(request.getContextPath() + result);
	}


	private void forward(String url) throws ServletException, IOException{
		request.getRequestDispatcher(url).forward(request, response);
	}
	/**
	 * 把ModelMap中的数据放入session中
	 * 
	 * @param modelMap
	 */
	private void putSessionAttribute(ModelMap modelMap) {
		// 向session中放入参数
		Map<String, Object> sessionMap = modelMap.getSessionMap();
		Set<Entry<String, Object>> sessionSet = sessionMap.entrySet();
		for (Entry<String, Object> entry : sessionSet) {
			this.request.getSession().setAttribute(entry.getKey(),
					entry.getValue());
		}
	}

	/**
	 * 把ModelMap中的数据放入request中
	 * 
	 * @param modelMap
	 */
	private void putRequestAttribute(ModelMap modelMap) {
		// 向request中放入参数
		Map<String, Object> requestMap = modelMap.getRequestMap();
		Set<Entry<String, Object>> set = requestMap.entrySet();
		for (Entry<String, Object> entry : set) {
			this.request.setAttribute(entry.getKey(), entry.getValue());
		}
	}
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
}
