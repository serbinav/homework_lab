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
		output.append("Пицца размером: " + this.size);
		output.append(". Состаящая из: ");
		for (int i = 0; i < listIngredients.size(); i++) {
			output.append(listIngredients.get(i).getName());
			output.append(" - ");
			output.append(listIngredients.get(i).getNumber());
			if (i + 1 < listIngredients.size()) {
				output.append(", ");
			}
		}
		return output.toString();
	}
}
