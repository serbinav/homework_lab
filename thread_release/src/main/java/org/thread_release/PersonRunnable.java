package org.thread_release;

import java.util.Random;

import org.printing_module.Person;
import org.printing_module.Task;

public class PersonRunnable implements Runnable{
	
	private TaskQueue cookQueue;
	private Person client;
	
	public PersonRunnable(TaskQueue cookQueue, int num) {
		this.client = new Person(num);
		this.cookQueue = cookQueue;
	}
	
	@Override
	public void run() {
		Random random = new Random();

		int number = 0;
		number = random.nextInt(5000);
		try {
			Thread.sleep(number);
		} catch (InterruptedException e) {
			System.err.println("Ошибка sleep PersonRunnable: " + e);
		}

		Task newTask = this.client.createTask();
		newTask.setClient(this.client);
		this.cookQueue.put(newTask);
		
//		- поступил заказ №1 тот ли???
//		от клиента №1 (состав:…); +
//		очередь заказов:…, +
//		склад:   -

		System.out.println("Поступил заказ № "+newTask.getClient().getNumber() +" от " + newTask.getClient().getNamePerson()+"(" + newTask.printTask() + ")");
		StringBuilder tmpString = new StringBuilder();
		if (cookQueue.size() > 0) {
			for (int i = 0; i < cookQueue.size(); i++) {
				tmpString.append(" № " + cookQueue.get(i).getClient().getNumber() + " - "
						+ cookQueue.get(i).getClient().getNamePerson() + " ");
			}
			System.out.println("очередь заказов: " + tmpString.toString());
		}	
	}
}
