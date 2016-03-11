package org.printing_module;

import java.io.Serializable;

public class Ingredient implements Serializable {
	private static final long serialVersionUID = -4803646339631716286L;
	private String name;
	private int number;

	public Ingredient(String name, int number) {
		this.name = name;
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
