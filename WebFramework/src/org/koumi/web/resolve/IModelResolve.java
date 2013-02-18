package org.koumi.web.resolve;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IModelResolve {
	boolean getIsResolve();
	
	void setResponse(HttpServletResponse response);

	void modelResolve(Object result) throws ServletException, IOException;

	void setRequest(HttpServletRequest request);

}