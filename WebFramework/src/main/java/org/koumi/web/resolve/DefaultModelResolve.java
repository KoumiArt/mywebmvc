package org.koumi.web.resolve;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能：负责把controller返回的类型进行解析
 * @author Koumi
 *
 */
public class DefaultModelResolve implements IModelResolve {
	private boolean isResolve = false; 

	/**
	 * 模型解析方法，可以自己拓展，只需实现IModelReslove接口，并且在config.xml中配置一个<web-model-resolve class=""/>
	 * @param result 控制器返回的结果
	 * @throws ServletException
	 * @throws IOException
	 */
	public void modelResolve(Object result) throws ServletException, IOException {
		this.isResolve = true;
	}


	public void setRequest(HttpServletRequest request) {
	}

	public void setResponse(HttpServletResponse response) {
	}


	public boolean getIsResolve() {
		return this.isResolve;
	}
	
}
