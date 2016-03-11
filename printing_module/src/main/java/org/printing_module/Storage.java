package org.printing_module;

import java.util.List;

public interface Storage {
	
	// получить список компонентов
	List<Ingredient> getListComponent();
	
	// установить список компонентов
	void setListComponent(List<Ingredient> listComponent);
		
	// получить количество ингридиента по его имени
	int getCountByName(String name);

	// получить индекс ингридиента по его имени
	int getIndexByName(String name);
	
	// печать списка компонентов
	String printListComponent();

}
