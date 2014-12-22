package org.springframework.test.context.junit4;

import javax.naming.NamingException;

import org.junit.runners.model.InitializationError;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

public class SpringJUnit4ClassRunnerJNDI extends SpringJUnit4ClassRunner {

	public static boolean isJNDIactive;

	public SpringJUnit4ClassRunnerJNDI(Class<?> clazz)
			throws InitializationError, IllegalStateException, NamingException {
		super(clazz);

		synchronized (SpringJUnit4ClassRunnerJNDI.class) {
			if (!isJNDIactive) {

				@SuppressWarnings("resource")
				ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextTestDS.xml");
				SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
				builder.bind("java:/comp/env/jdbc/APP_CONELE", applicationContext.getBean("dataSourceCONELE"));
				builder.activate();

				isJNDIactive = true;
			}
		}
	}

}
