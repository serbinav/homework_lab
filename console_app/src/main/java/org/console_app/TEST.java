package org.console_app;

import java.util.ArrayList;
import java.util.List;

import org.console_release.StorageInCollectionThread;
import org.console_release.StorageInList;
import org.printing_module.Ingredient;
import org.printing_module.Person;
import org.printing_module.ProductionPizza;
import org.printing_module.Storage;
import org.printing_module.Task;

public class TEST {

	public static void main(String[] args) {
		
		List<Task> listTask;

		Storage newStorage = new StorageInList();
		List<Ingredient> listComponent = new ArrayList<>();

		listComponent.add(new Ingredient("сыр", 100));
		listComponent.add(new Ingredient("колбаса", 100));
		listComponent.add(new Ingredient("перец", 100));
		newStorage.setListComponent(listComponent);
		
        int nThreads = 5;		
        for (int i=0; i<nThreads; i++) {
        	Person tmpPerson = new Person(i,1000);
        	tmpPerson.start();
        	
			ProductionPizza once = new StorageInCollectionThread();
			int sizePizza = once.selectPizzaSize();
			while (sizePizza <= 0 || sizePizza > 4100) {
				sizePizza = once.selectPizzaSize();
			}
			
			tmpPerson.getSinglePizza().setSizePizza(sizePizza);
	
			tmpPerson.getSinglePizza().setListIngredients(once.chooseIngredientForPizza(newStorage));
	
			once.printOrderPizza(tmpPerson.getSinglePizza());
        }		
	}
}
