/**
 * 
 */
package com.ebao.framework.assembly;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Coral.Ma
 * 
 */
public class BeanFactory implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		applicationContext = context;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static <T> T getBean(String key, Class<T> cls) {
		return applicationContext.getBean(key, cls);
	}
	
	/**
	 * The bean id name is same as the class name.
	 * @param <T>
	 * @param cls
	 * @return <T>
	 */
	public static <T> T getBean(Class<T> cls) {
		return applicationContext.getBean(cls.getSimpleName(), cls);
	}
	
	public static Object getBean(String key) {
		return applicationContext.getBean(key);
	}
}