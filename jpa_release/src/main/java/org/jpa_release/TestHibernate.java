package org.jpa_release;

import org.hibernate.Session; 
 
public class TestHibernate { 
 
    public static void main(String[] args) { 
        Session session = HibernateUtil.getSessionFactory().openSession(); 
        session.beginTransaction(); 
 
        // Add new Employee object 
        EmployeeEntity emp = new EmployeeEntity(); 
        emp.setEmail("demo-user@mail.com"); 
        emp.setFirstName("demo"); 
        emp.setLastName("user"); 
 
        session.save(emp); 
 
        session.getTransaction().commit(); 
        HibernateUtil.shutdown(); 
    } 
} 