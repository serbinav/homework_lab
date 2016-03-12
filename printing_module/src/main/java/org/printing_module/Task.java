package org.printing_module;

import java.util.Collections;
import java.util.List;

public class Task {
	
	private int size;
	private List<Ingredient> listIngredients;
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<Ingredient> getListIngredients() {
		return Collections.unmodifiableList(listIngredients);

	}
	public void setListIngredients(List<Ingredient> listIngredients) {
		this.listIngredients = listIngredients;
	}
}
