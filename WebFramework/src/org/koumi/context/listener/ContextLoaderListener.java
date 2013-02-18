package org.koumi.context.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.koumi.exception.MyException;
import org.koumi.util.XMLHelper;

public class ContextLoaderListener implements ServletContextListener{
	private String configLocation ;
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		configLocation = sce.getServletContext().getInitParameter("configLocation");
		configLocation = configLocation.startsWith("\\")?configLocation : "\\" + configLocation;
		XMLHelper.configLocation = sce.getServletContext().getRealPath("")+configLocation.trim();
		try {
			ContextLoader contextLoader = new ContextLoader();
			contextLoader.load();
		}  catch (MyException e) {
			e.printStackTrace();
		}
	}

}
