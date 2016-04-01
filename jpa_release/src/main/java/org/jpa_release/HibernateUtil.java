package org.jpa_release;
 
import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration; 

            public class HibernateUtil {
            	private static final SessionFactory sessionFactory = buildSessionFactory();
            	 
	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			// return new AnnotationConfiguration().configure(new
			// File("C:\\WORK\\GitHub\\homework_lab\\jpa_release\\hibernate.cgf.xml")).buildSessionFactory();

			
			File conf = new File("C:\\WORK\\GitHub\\homework_lab\\jpa_release\\src\\main\\java\\org\\jpa_release\\hibernate.cfg.xml");
			
			//return new Configuration().configure(conf).buildSessionFactory();
			
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
