package org.koumi.util;

import java.io.InputStream;

public class ClassLoaderWrapper {
	ClassLoader defaultClassLoader;
	ClassLoader systemClassLoader;

	public ClassLoaderWrapper() {
		try {
			systemClassLoader = ClassLoader.getSystemClassLoader();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	public InputStream getResourceAsStream(ClassLoader classLoader,
			String resource) {
		return getResourceAsStream(resource, getClassLoaders(classLoader));
	}

	private InputStream getResourceAsStream(String resource,
			ClassLoader[] classLoaders) {
		for (ClassLoader classLoader : classLoaders) {
			if (classLoader != null) {
				InputStream returnValue = classLoader
						.getResourceAsStream(resource);
				if (returnValue == null)
					classLoader.getResource("/" + resource);
				if (returnValue != null)
					return returnValue;
			}
		}
		return null;
	}

	ClassLoader[] getClassLoaders(ClassLoader classLoader) {
		return new ClassLoader[] { classLoader, defaultClassLoader,
				Thread.currentThread().getContextClassLoader(),
				getClass().getClassLoader(), systemClassLoader };
	}

	public Class<?> classForName(String className) throws ClassNotFoundException {
		return classForName(className, getClassLoaders(null));
	}

	Class<?> classForName(String className, ClassLoader[] classLoaders) throws ClassNotFoundException{
		for (ClassLoader classLoader : classLoaders) {
			if (classLoader != null) {
				try {
					Class<?> clazz = Class
							.forName(className, true, classLoader);
					if (clazz != null)
						return clazz;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
