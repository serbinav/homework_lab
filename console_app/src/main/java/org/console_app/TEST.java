package org.console_app;

import java.util.ArrayList;
import java.util.List;

import org.console_release.StorageInCollectionThread;
import org.console_release.StorageInList;
import org.printing_module.Ingredient;
import org.printing_module.Pizza;
import org.printing_module.ProductionPizza;
import org.printing_module.Storage;

public class TEST {

	public static void main(String[] args) {

		Storage newStorage = new StorageInList();
		Pizza newPizza = new Pizza();
		List<Ingredient> listComponent = new ArrayList<>();

		listComponent.add(new Ingredient("сыр", 100));
		listComponent.add(new Ingredient("колбаса", 100));
		listComponent.add(new Ingredient("перец", 100));
		newStorage.setListComponent(listComponent);

		ProductionPizza once = new StorageInCollectionThread();
		int sizePizza = once.selectPizzaSize();
		while (sizePizza <= 0 || sizePizza > 4100) {
			sizePizza = once.selectPizzaSize();
		}
		newPizza.setSizePizza(sizePizza);

		newPizza.setListIngredients(once.chooseIngredientForPizza(newStorage));

		once.printOrderPizza(newPizza);
	}
}
