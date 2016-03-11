package org.printing_module;

import java.util.Collections;
import java.util.List;

public class Pizza {
	private int sizePizza;
	private List<Ingredient> listIngredients;

	public Pizza() {
	}
	
	public Pizza(int size, List<Ingredient> listIngr) {
		this.sizePizza = size;
		this.listIngredients = listIngr;
	}
	
	public int getSizePizza() {
		return sizePizza;
	}

	public void setSizePizza(int sizePizza) {
		this.sizePizza = sizePizza;
	}

	public List<Ingredient> getListIngredients() {
		return Collections.unmodifiableList(listIngredients);
	}

	public void setListIngredients(List<Ingredient> listIngredients) {
		this.listIngredients = listIngredients;
	}
	
	public String printPizza() {
		StringBuilder output = new StringBuilder();
		output.append("Вы заказали пиццу размером: " + this.sizePizza);
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
