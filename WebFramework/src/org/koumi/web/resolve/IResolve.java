package org.koumi.web.resolve;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * �����Ľӿ�
 * @author Koumi
 *
 */
public interface IResolve {
	/**
	 * ���ڽ���
	 * @param inputName Ҫ����@param��ֵ
	 * @return ��������ԵĶ���ʵ�����磺����java.util.Date,����Ҫ����һ��java.util.Date����ʵ��
	 * @throws ServletException
	 * @throws IOException
	 */
	public Object resolve(String inputName) throws ServletException, IOException;

	/**
	 * request����
	 * @param request
	 */
	public void setRequest(HttpServletRequest request);
}
