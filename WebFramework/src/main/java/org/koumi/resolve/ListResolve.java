package org.koumi.resolve;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.koumi.annotation.FromResolve;
import org.koumi.web.resolve.IResolve;
@FromResolve("java.util.List")
public class ListResolve implements IResolve{
	HttpServletRequest request;
	public Object resolve(String inputName) throws ServletException,
			IOException {
		String[] parameterValues = inputName == null?null :request.getParameterValues(inputName);
		if(parameterValues != null && parameterValues.length > 0){
			return Arrays.asList(parameterValues);
		}
		return null;
	}

	@Override
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}
