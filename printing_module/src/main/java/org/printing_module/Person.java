package org.printing_module;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Person {
	private int number;
	private String namePerson;
	private static Random randomIn;

	public Person() {
		randomIn = new Random();
		
		UUID id = UUID.randomUUID();
		this.setNamePerson(id.toString().replaceAll("-",""));
	}

	public Person(int number){
		randomIn = new Random();
		this.number = number;	

		UUID id = UUID.randomUUID();
		this.setNamePerson(id.toString().replaceAll("-",""));
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
				
		component.add(new Ingredient("сыр", randomInt()));
		component.add(new Ingredient("колбаса", randomInt()));
		component.add(new Ingredient("перец", randomInt()));
		
		newPizza.setListIngredients(component);
		
		return newPizza;
	}
	
	// ----------------------------------Random--------------------------------------------
	public int randomInt() {
		int number = 0;
		number = randomIn.nextInt(10);
		return number;
	}
	
	public String randomIngredient() {
		int number = 0;
		number = randomIn.nextInt(3)+1;
	
		if (number == 1) {
			return "сыр";
		} else if (number == 2) {
			return "колбаса";
		} else{
			return "перец";	
		}
	}
}