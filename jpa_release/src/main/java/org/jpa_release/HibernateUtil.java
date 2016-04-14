package org.jpa_release;
 
import java.io.File;

import org.apache.log4j.PropertyConfigurator;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			// return new AnnotationConfiguration().configure(new
			// File("C:\\WORK\\GitHub\\homework_lab\\jpa_release\\hibernate.cgf.xml")).buildSessionFactory();

			File tmp = new File("");

	    	StringBuilder hibernateConfPath = new StringBuilder();
	    	hibernateConfPath.append(tmp.getAbsolutePath());
	    	hibernateConfPath.append(File.separator);
	    	hibernateConfPath.append("src");
	    	hibernateConfPath.append(File.separator);
	    	hibernateConfPath.append("main");
	    	hibernateConfPath.append(File.separator);
	    	hibernateConfPath.append("java");
	    	hibernateConfPath.append(File.separator);
	    	hibernateConfPath.append("org");
	    	hibernateConfPath.append(File.separator);
	    	hibernateConfPath.append("jpa_release");
	    	hibernateConfPath.append(File.separator);
	    	hibernateConfPath.append("hibernate.cfg.xml");

	    	System.out.println("Absolute Pathname "+ hibernateConfPath.toString());
	    	PropertyConfigurator.configure(hibernateConfPath.toString());
		
	    	File conf = new File(hibernateConfPath.toString());

			// return new Configuration().configure(conf).buildSessionFactory();

			return new AnnotationConfiguration().configure(conf).buildSessionFactory();

			// return new
			// File(SessionFactory.class.getClassLoader().getResource("hibernate.cfg.xml").getFile())).buildSessionFactory();
			// return new File("hibernate.cgf.xml")).buildSessionFactory();

		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
}
