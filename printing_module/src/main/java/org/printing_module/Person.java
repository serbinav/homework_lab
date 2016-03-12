package org.printing_module;

import java.util.UUID;

public class Person extends Thread {
	private int number;
	private String namePerson;
	private Pizza singlePizza;
	private int executionTime;

	public Person() {
	}
	
	public Person(int number, int input){
		this.number = number;	
		this.singlePizza = new Pizza();
		this.executionTime = input;	

		UUID id = UUID.randomUUID();
		this.namePerson = id.toString().replaceAll("-","");
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
	
	@Override
	public void run()
	{
		try{
			sleep(executionTime);		//Приостанавливает поток на ***
			}catch(InterruptedException e){
				System.out.println("яйцо!");		
			}
		System.out.println(number + " " + namePerson + " !");	
	}
	
	public void createTask() {
	
	}

}