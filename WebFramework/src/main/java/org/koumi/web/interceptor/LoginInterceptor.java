package org.koumi.web.interceptor;

import org.koumi.annotation.Interceptor;
import org.koumi.web.model.ModelMap;

@Interceptor
public class LoginInterceptor {

	public String invoke(ModelMap modelMap) {		
		if (modelMap.getResult().contains("login.do")) {
			return null;
		} else {
			String userName = (String) modelMap
					.getSessionetAttribute("userName");
			if (userName == null) {
				return "/login.jsp";
			}
		}
		return null;
	}

}
