package org.printing_module;

import java.util.Collections;
import java.util.List;

public class Task {
	
	private int size;
	private List<Ingredient> listIngredients;
	private Person client;

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
	
	public Person getClient() {
		return client;
	}
	public void setClient(Person client) {
		this.client = client;
	}
	
	public String printTask() {
		StringBuilder output = new StringBuilder();
		output.append("Вы заказали пиццу размером: " + this.size);
		output.append(" Состаящую из: ");
		for (Ingredient ingredient : listIngredients) {
			output.append(ingredient.getName());
			output.append(" ");
			output.append(ingredient.getNumber());
			output.append(" ");
		}
		return output.toString();
	}
}
