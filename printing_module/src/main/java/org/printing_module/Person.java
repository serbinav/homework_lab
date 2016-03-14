package org.printing_module;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Person {
	private int number;
	private String namePerson;
	private Pizza singlePizza;
	private static Random randomIn;

	public Person() {
		randomIn = new Random();
	}

	public Person(int number){
		randomIn = new Random();

		this.number = number;	
		this.singlePizza = new Pizza();

		UUID id = UUID.randomUUID();
		this.setNamePerson(id.toString().replaceAll("-",""));
	}
	
	public Pizza getSinglePizza() {
		return singlePizza;
	}

	public void setSinglePizza(Pizza singlePizza) {
		this.singlePizza = singlePizza;
	}
		
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getNamePerson() {
		return namePerson;
	}

	public void setNamePerson(String namePerson) {
		this.namePerson = namePerson;
	}
	
	public Task createTask() {
		
		Task newPizza = new Task();
		
		newPizza.setSize(randomInt());

		List<Ingredient> component = new ArrayList<>();
		
		for (int r = 0; r < 3; ++r){
			String ingrName = randomIngredient();
			int ingrNumber = randomInt();
			component.add(new Ingredient(ingrName, ingrNumber));
		}
		
		newPizza.setListIngredients(component);
		
		return newPizza;
	}
	
	// ----------------------------------Random--------------------------------------------
	public int randomInt() {
		int number = 0;
		number = randomIn.nextInt(10);
		System.out.println(number);
		return number;
	}
	
	public String randomIngredient() {
		int number = 0;
		number = randomIn.nextInt(3)+1;
	
		if (number == 1) {
			System.out.println("сыр");
			return "сыр";
		} else if (number == 2) {
			System.out.println("колбаса");
			return "колбаса";
		} else{
			System.out.println("перец");
			return "перец";	
		}
	}
}