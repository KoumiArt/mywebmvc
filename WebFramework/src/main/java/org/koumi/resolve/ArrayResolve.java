package org.koumi.resolve;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.koumi.annotation.FromResolve;
import org.koumi.web.resolve.IResolve;
@FromResolve("java.util.List")
public class ArrayResolve implements IResolve{
	HttpServletRequest request;
	public Object resolve(String inputName) throws ServletException,
			IOException {
		
		return null;
	}

	@Override
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}
