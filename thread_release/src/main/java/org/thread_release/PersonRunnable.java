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
		number = random.nextInt(1000);
		try {
			Thread.sleep(number);
		} catch (InterruptedException e) {
			System.err.println("Ошибка sleep PersonRunnable: " + e);
		}

		Task newTask = this.client.createTask();
		newTask.setClient(this.client);
		this.cookQueue.put(newTask);
	}
}
