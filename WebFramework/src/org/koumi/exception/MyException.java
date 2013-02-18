package org.koumi.exception;

/**
 * bean 参数解析异常
 * @author Koumi
 *
 */
public class MyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public MyException() {
		super();
	}
	
	public MyException(String messages) {
		super(messages);
	}
	
	public MyException(Throwable throwable){
		super(throwable);
	}
	
	public MyException(String messages,Throwable throwable) {
		super(messages,throwable);
	}
}
