package org.thread_release;

import org.printing_module.Person;
import org.printing_module.Task;

public class PersonRunnable implements Runnable{
	
	private TaskQueue cookQueue;
	private Person client;

	public PersonRunnable(TaskQueue cookQueue) {
		this.client = new Person();
		this.cookQueue = cookQueue;
	}
	
	public PersonRunnable(TaskQueue cookQueue, int num) {
		this.client = new Person(num);
		this.cookQueue = cookQueue;
	}
	
	@Override
	public void run() {
		Task newTask = this.client.createTask();
		newTask.setClient(this.client);
		this.cookQueue.put(newTask);
	}
}
