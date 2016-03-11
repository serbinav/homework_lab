package org.console_release;

import java.util.Collections;
import java.util.List;

import org.printing_module.Ingredient;
import org.printing_module.Pizza;
import org.printing_module.Storage;

public class StorageInList implements Storage {
	private Pizza singlePizza;
	private List<Ingredient> listComponent;

	public Pizza getSinglePizza() {
		return singlePizza;
	}

	public void setSinglePizza(Pizza singlePizza) {
		this.singlePizza = singlePizza;
	}

	@Override
	public List<Ingredient> getListComponent() {
		return Collections.unmodifiableList(listComponent);
	}

	@Override
	public void setListComponent(List<Ingredient> listComponent) {
		this.listComponent = listComponent;
	}

	@Override
	public int getCountByName(String name) {
		for (Ingredient ingredient : listComponent) {
			if (ingredient.getName().compareTo(name) == 0) {
				return ingredient.getNumber();
			}
		}
		return -1;
	}
	
	@Override
	public int getIndexByName(String name) {
		for (int i = 0; i < listComponent.size(); i++) {
			if (listComponent.get(i).getName().compareTo(name) == 0) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public String printListComponent() {
		StringBuilder output = new StringBuilder();
		output.append("На складе имеется: ");
		for (Ingredient ingredient : listComponent) {
			output.append(ingredient.getName());
			output.append(" ");
			output.append(ingredient.getNumber());
			output.append(" ");
		}
		return output.toString();
	}
}
