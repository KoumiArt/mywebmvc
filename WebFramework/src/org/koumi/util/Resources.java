package org.koumi.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Resources {
	private static ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper();
	public Resources() {
	}

	public static Reader getResourceAsReader(String resource) throws IOException {
		Reader reader = new InputStreamReader(getResurceAsStream(resource));
		return reader;
	}

	public static InputStream getResurceAsStream(String resource) throws IOException {
		return getResurceAsStream(null,resource);
	}
	
	public static InputStream getResurceAsStream(ClassLoader classLoader , String resource) throws IOException {
		resource = resource.startsWith("\\")?resource:"\\"+resource;
		InputStream in = classLoaderWrapper.getResourceAsStream(classLoader,resource.substring(resource.lastIndexOf("\\")));
		if(in==null) {
			in = getResurcePathAsStream(resource);
			if(in == null ){
				throw new IOException("Could not find resource " + resource);
			}
		}
		return in;
	}
	
	public static InputStream getResurcePathAsStream(String resource) throws IOException {
		InputStream in = null;
		in = new FileInputStream(resource);
		return in;
	}

	public static Class<?> classForName(String className) throws ClassNotFoundException{
		return classLoaderWrapper.classForName(className);
	}
}
