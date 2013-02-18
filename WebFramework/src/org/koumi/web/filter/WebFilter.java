package org.koumi.web.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.koumi.util.ReflectUtil;
import org.koumi.util.Resources;
import org.koumi.util.XMLHelper;
import org.koumi.web.model.ModelMap;

/**
 * @author Koumi
 *
 */
public class WebFilter implements Filter{
	private ModelMap modelMap;
	private String configLocation ;
	
	public void destroy() {
		
	}

	/* 
	 * 获取config文件中的web-interceptor的class,并且此class必须是有个invoke方法,并且方法的参数为ModelMap
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		modelMap.setResult(getHttpServletRequest(servletRequest).getRequestURI());
		putSessionAttributeToModelMap(servletRequest);
		putRequestAttributeToModelMap(servletRequest);
		//读取interceptors
		List<String> interceptors = XMLHelper.getAttributes("web-interceptor", "class");
		if(null != interceptors) {
			for (String interceptor : interceptors) {
				try {
					Object obj = Resources.classForName(interceptor).newInstance();
					Method method = ReflectUtil.getObjectMethod(obj, "invoke", ModelMap.class);
					method.setAccessible(true);
					Object result = method.invoke(obj,modelMap);
					if(result != null){
						servletRequest.getRequestDispatcher(result.toString()).forward(servletRequest, servletResponse);
						return ;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		chain.doFilter(servletRequest, servletResponse);
	}

	/**
	 * 设置SessionAttribut到ModelMap中
	 * @param servletRequest
	 */
	private void putSessionAttributeToModelMap(ServletRequest servletRequest) {
		HttpSession session = getSession(servletRequest);
		Enumeration<String> sessionAttribute =session.getAttributeNames();
		while (sessionAttribute.hasMoreElements()) {
			String  attributName = sessionAttribute.nextElement();
			modelMap.putSessionAttribute(attributName, session.getAttribute(attributName));
		}
	}
	
	/**
	 * 设置requestAttribut到ModelMap中
	 * @param request
	 */
	private void putRequestAttributeToModelMap(ServletRequest request) {
		Enumeration<String> requestAttribute = request.getAttributeNames();
		while (requestAttribute.hasMoreElements()) {
			String  attributName = requestAttribute.nextElement();
			System.out.println(attributName);
			modelMap.putRequsetAttribute(attributName, request.getAttribute(attributName));
		}
	}

	private HttpServletRequest getHttpServletRequest(ServletRequest servletRequest){
		return  (HttpServletRequest)servletRequest;
	}
	
	private HttpSession getSession(ServletRequest servletRequest) {
		HttpSession session = ((HttpServletRequest)servletRequest).getSession();
		return session;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		modelMap = new ModelMap();
		configLocation = filterConfig.getServletContext().getInitParameter("configLocation");
		configLocation = configLocation.startsWith("\\")?configLocation : "\\" + configLocation;
		XMLHelper.configLocation = filterConfig.getServletContext().getRealPath("")+configLocation.trim();
	}

}
