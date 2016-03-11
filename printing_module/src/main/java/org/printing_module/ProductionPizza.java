package org.printing_module;

import java.util.List;

public interface ProductionPizza {
	
	// выбор размера пиццы;
	int selectPizzaSize();

	// выбор ингредиента и его количества
	List<Ingredient> chooseIngredientForPizza(Storage hangar);

	// выдача заказа (вывести на экран заказанную пиццу)
	void printOrderPizza(Pizza input);
	
	// печать справки
	void printHelp();
}
