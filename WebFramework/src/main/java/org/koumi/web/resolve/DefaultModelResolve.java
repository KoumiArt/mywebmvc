package org.koumi.web.resolve;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ���ܣ������controller���ص����ͽ��н���
 * @author Koumi
 *
 */
public class DefaultModelResolve implements IModelResolve {
	private boolean isResolve = false; 

	/**
	 * ģ�ͽ��������������Լ���չ��ֻ��ʵ��IModelReslove�ӿڣ�������config.xml������һ��<web-model-resolve class=""/>
	 * @param result ���������صĽ��
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
