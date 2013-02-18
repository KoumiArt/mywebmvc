package org.koumi.web.resolve;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * 解析的接口
 * @author Koumi
 *
 */
public interface IResolve {
	/**
	 * 用于解析
	 * @param inputName 要进行@param的值
	 * @return 解析后相对的对象实例，如：解析java.util.Date,则需要返回一个java.util.Date对象实例
	 * @throws ServletException
	 * @throws IOException
	 */
	public Object resolve(String inputName) throws ServletException, IOException;

	/**
	 * request对象
	 * @param request
	 */
	public void setRequest(HttpServletRequest request);
}
