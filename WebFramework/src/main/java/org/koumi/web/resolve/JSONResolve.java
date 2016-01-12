package org.koumi.web.resolve;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.koumi.exception.MyException;
import org.koumi.util.JsonUtil;

public class JSONResolve implements IModelResolve {
	private HttpServletResponse response;
	private boolean isResolve;

	public boolean getIsResolve() {
		return this.isResolve;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void modelResolve(Object result) throws ServletException,
			MyException {
		try {
			// 向客户端写入JSONBean
			response.setContentType("text/json,charset=utf-8");
			response.setCharacterEncoding("gbk");
			response.getWriter().println(JsonUtil.toJson(result));
			this.isResolve = true;
		} catch (Exception ex) {
			this.isResolve = false;
			ex.printStackTrace();
			throw new MyException("JSON解析错误！" + ex.getMessage());
		}

	}

	public void setRequest(HttpServletRequest request) {
	}

}
