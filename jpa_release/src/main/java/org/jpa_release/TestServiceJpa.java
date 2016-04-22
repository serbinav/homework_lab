package org.jpa_release;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.postgresql.ds.PGPoolingDataSource;
import org.printing_module.Person;
import org.printing_module.Task;
 
public class TestServiceJpa {
 
	@Test
    public void testAll() {
    	StorageService serviceStorage = new StorageService();
    	PizzaService servicePizza = new PizzaService();
    	
/*        List<StorageEntity> store = service.getAll();
        
        for(StorageEntity s : store){
            System.out.println(s.getIdIngr().getName());
            System.out.println(s.getNumberIngr());
        }
*/        
        
//insert Storage
        IngredientDictEntity ingr1 = new IngredientDictEntity(); 
        ingr1.setId(1); 
        ingr1.setName("сыр"); 

        StorageEntity stor1 = new StorageEntity(); 
        stor1.setIdIngr(ingr1); 
        stor1.setNumberIngr(100); 
/*
        stor1 = service.add(stor1);
*/        
//select Storage
        StorageEntity storFromDB = serviceStorage.findByName("сыр");
        System.out.println(storFromDB);
        
//update Storage
        storFromDB.setNumberIngr(50);
 
        serviceStorage.update(storFromDB);
 
        //StorageEntity stor2 = service.get(stor1.getId());
        //System.out.println(stor2);
        
//select Pizza
        System.out.println(servicePizza.getMax());   
        
//insert Pizza
        IngredientDictEntity ingr2 = new IngredientDictEntity(); 
        ingr1.setId(2); 
        ingr1.setName("колбаса"); 

        PizzaEntity piz1 = new PizzaEntity(); 
        piz1.setIdPizza(1); 
        piz1.setSize(7); 
        piz1.setIdIngr(ingr1); 
        piz1.setNumberIngr(8); 

        piz1 = servicePizza.add(piz1);  
    }	
//****************************************************************************************

	// ок
	public long selectStorage(StorageService serviceStorage) {
		long count = 0;
		try {
			count = serviceStorage.getCount();
	        System.out.println(count);   
		} catch (Exception e) {
			System.err.println("Ошибка в проверке наличия ингридиентов на складе: " + e);
		}
		return count;
	}
		
Ошибка при проверке списка готовых пицц: java.lang.NullPointerException;
	public int selectPizza(PizzaService servicePizza) {
		int max = 0;
		try {
			max = servicePizza.getMax();		        
			System.out.println(max);   
		} catch (Exception e) {
			System.err.println("Ошибка при проверке списка готовых пицц: " + e);
		}
		return max;
	}

	// ок
	public int selectIngredient(StorageService serviceStorage, String ingrName) {
		int ingrNumberStorage = 0;
		try {
	        StorageEntity storFromDB = serviceStorage.findByName(ingrName);
	        ingrNumberStorage = storFromDB.getNumberIngr();
	        System.out.println(storFromDB.getNumberIngr());	
		} catch (Exception e) {
			System.err.println("Ошибка в получении количества ингридиентов со склада: " + e);
		}
		return ingrNumberStorage;
	}

Нужно проверить;
	public void insertStorage(StorageService serviceStorage) {
		try {
			for (int i = 1; i < 4; i++) {
		        IngredientDictEntity ingr = new IngredientDictEntity(); 
		        ingr.setId(i); 

		        StorageEntity stor1 = new StorageEntity(); 
		        stor1.setIdIngr(ingr); 
		        stor1.setNumberIngr(100); 

		        stor1 = serviceStorage.add(stor1);
			}
		} catch (Exception e) {
			System.err.println("Ошибка при добавлении ингридиентов на склад: " + e);
		}
	}

	public void insertPizzaUpdateStorage(IngredientDictService ingredientService, PizzaService servicePizza , StorageService serviceStorage, Task newTask, int nextNumber) {
		System.out.println("Забираем игридиенты для приготовления пиццы");
		try {
			for (int p = 0; p < newTask.getListIngredients().size(); p++) {
				String ingrName = newTask.getListIngredients().get(p).getName();
				int ingrNumber = newTask.getListIngredients().get(p).getNumber();

				//insert Pizza
		        IngredientDictEntity ingr = ingredientService.findByName(ingrName); 
		        
		        PizzaEntity piz1 = new PizzaEntity(); 
		        piz1.setIdPizza(nextNumber); 
		        piz1.setSize(newTask.getSize()); 
		        piz1.setIdIngr(ingr); 
		        piz1.setNumberIngr(ingrNumber); 

		        piz1 = servicePizza.add(piz1);
		    
				//update Storage
		        StorageEntity storFromDB = serviceStorage.findByName(ingrName);
		        storFromDB.setNumberIngr(storFromDB.getNumberIngr() - ingrNumber);
		        serviceStorage.update(storFromDB);
			}
			
		} catch (Exception e) {
			System.err
					.println("Ошибка при добавлении пиццы в список готовых или вычитании ингридиентов со склада: " + e);
		}
	}

	public boolean checkIngrNumber(StorageService serviceStorage, Task newTask) {
		System.out.println("Проверям наличие ингридиентов на складе");
		for (int q = 0; q < newTask.getListIngredients().size(); q++) {
			String ingrName = newTask.getListIngredients().get(q).getName();
			int ingrNumber = newTask.getListIngredients().get(q).getNumber();
			
			if (selectIngredient(serviceStorage, ingrName) < ingrNumber) {
				System.err.println("Ошибка: недостаточно ингридиента: \"" + ingrName + "\" для обработки заказа");
				return true;
			}
		}
		return false;
	}
	
//----------------------------------------------------------------------------------------
	public static void main(String[] args) {
		TestServiceJpa app = new TestServiceJpa();
		
		IngredientDictService ingredientService = new IngredientDictService();
    	PizzaService servicePizza = new PizzaService();
    	StorageService serviceStorage = new StorageService();

		try {
			if (app.selectStorage(serviceStorage) == 0) {
				System.err.println("Ошибка: склад пуст, ингридиенты будут добавлены");

				app.insertStorage(serviceStorage);

			} else {
				System.out.println("На складе присутствуют запасы");
			}

			Person client = new Person(1);
			Task newTask = client.createTask();
			System.out.println("Сгенерировали заказ");

			if (app.checkIngrNumber(serviceStorage, newTask) == true) {
				return;
			}

			int nextNumber = app.selectPizza(servicePizza);

			if (nextNumber == 0) {
				nextNumber = 1;
			} else {
				nextNumber = nextNumber + 1;
			}

			app.insertPizzaUpdateStorage(ingredientService,servicePizza,serviceStorage, newTask, nextNumber);

			System.out.println("Пицца готова");
		} catch (Exception e) {
			System.err.println("Ошибка: " + e);
		}
	}
}
