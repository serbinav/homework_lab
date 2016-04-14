package org.jpa_release;

import java.io.File;

import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Session; 
 
public class TestHibernate { 
	
	//private static EntityManager em = Persistence.createEntityManagerFactory("SSTestUnit").createEntityManager();
	
	// SELECT * FROM storage
	private static final String SELECT_COUNT_STORAGE = "SELECT count(*) FROM storage";
	
	// ok
	private static final String INSERT_STORAGE = "INSERT INTO storage (id, id_ingr, number_ingr) VALUES (?, ?, ?)";
	
	// ok  NamedQuery
	//private static final String SELECT_INGREDIENT_STORAGE ;
	
	//
	private static final String SELECT_MAX_PIZZA = "SELECT MAX(id_pizza) FROM pizza";
	
	// ok  NamedQuery
	private static final String INSERT_PIZZA = "INSERT INTO pizza(id_pizza, size, id_ingr, number_ingr) VALUES (?, ?, (SELECT id FROM ingredient_dict WHERE name = ?), ?)";
	
	// ok  NamedQuery
	private static final String UPDATE_STORAGE = "UPDATE storage SET number_ingr=number_ingr-? WHERE id_ingr = (SELECT id FROM ingredient_dict WHERE name = ?)";

    public static void main(String[] args) { 
    	
    	File f = new File("");
    		
    	StringBuilder log4jConfPath = new StringBuilder();
    	log4jConfPath.append(f.getAbsolutePath());
    	log4jConfPath.append(File.separator);
    	log4jConfPath.append("src");
    	log4jConfPath.append(File.separator);
    	log4jConfPath.append("main");
    	log4jConfPath.append(File.separator);
    	log4jConfPath.append("java");
    	log4jConfPath.append(File.separator);
    	log4jConfPath.append("org");
    	log4jConfPath.append(File.separator);
    	log4jConfPath.append("jpa_release");
    	log4jConfPath.append(File.separator);
    	log4jConfPath.append("log4j.properties");

    	System.out.println("Absolute Pathname "+ log4jConfPath.toString());
    	PropertyConfigurator.configure(log4jConfPath.toString());
    	
        Session session = HibernateUtil.getSessionFactory().openSession(); 
        session.beginTransaction(); 
 
        // Add new Employee object 
        /*EmployeeEntity emp = new EmployeeEntity(); 
        emp.setEmail("demo-user@mail.com"); 
        emp.setFirstName("demo"); 
        emp.setLastName("user"); */
        
        IngredientDictEntity ingr1 = new IngredientDictEntity(); 
        ingr1.setId(1); 
        ingr1.setName("сыр"); 
        IngredientDictEntity ingr2 = new IngredientDictEntity(); 
        ingr2.setId(2); 
        ingr2.setName("колбаса"); 
        IngredientDictEntity ingr3 = new IngredientDictEntity(); 
        ingr3.setId(3); 
        ingr3.setName("перец"); 

        StorageEntity stor1 = new StorageEntity(); 
        stor1.setIdIngr(ingr1); 
        stor1.setNumberIngr(100); 
        StorageEntity stor2 = new StorageEntity(); 
        stor2.setIdIngr(ingr2); 
        stor2.setNumberIngr(100); 
        StorageEntity stor3 = new StorageEntity(); 
        stor3.setIdIngr(ingr3); 
        stor3.setNumberIngr(100); 

        PizzaEntity pizz1 = new PizzaEntity(); 
        pizz1.setIdPizza(1); 
        pizz1.setSize(7); 
        pizz1.setIdIngr(ingr1); 
        pizz1.setNumberIngr(8); 
        PizzaEntity pizz2 = new PizzaEntity(); 
        pizz2.setIdPizza(1); 
        pizz2.setSize(7); 
        pizz2.setIdIngr(ingr2); 
        pizz2.setNumberIngr(5); 
        PizzaEntity pizz3 = new PizzaEntity(); 
        pizz3.setIdPizza(1); 
        pizz3.setSize(7); 
        pizz3.setIdIngr(ingr3); 
        pizz3.setNumberIngr(4); 
            
        session.save(ingr1); 
        session.save(ingr2); 
        session.save(ingr3); 
        session.save(stor1); 
        session.save(stor2);
        session.save(stor3); 
        session.save(pizz1); 
        session.save(pizz2); 
        session.save(pizz3); 
        
        session.getTransaction().commit(); 
        
        //StorageEntity country = (StorageEntity) em.createNativeQuery("Country.findByName", StorageEntity.class).getSingleResult();
        
        HibernateUtil.shutdown(); 
    }
} 