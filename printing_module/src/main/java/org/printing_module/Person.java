package org.printing_module;

import java.util.UUID;

public class Person {
	private int number;
	private String namePerson;
	private Pizza singlePizza;

	public Person() {
	}
	
	public Person(int number){
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
	
	public void createTask() {
	
	}

	public String getNamePerson() {
		return namePerson;
	}

	public void setNamePerson(String namePerson) {
		this.namePerson = namePerson;
	}

}