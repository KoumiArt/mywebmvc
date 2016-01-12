package org.koumi.resolve;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.koumi.util.DateUtils;
import org.koumi.web.resolve.IResolve;

public class LocalDateResolve implements IResolve {
	HttpServletRequest request;
	@Override
	public Object resolve(String inputName) throws ServletException, IOException {
		String inputValue = inputName == null?null : request.getParameter(inputName);
		return inputValue == null || inputValue.trim().length() <= 0 ? null : DateUtils.parse(inputValue.toString());
	}

	@Override
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

}
