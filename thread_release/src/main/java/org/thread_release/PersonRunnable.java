package org.thread_release;

import org.printing_module.Person;

public class PersonRunnable implements Runnable{
	
	private TaskQueue cookQueue;
	private Person client;

	public PersonRunnable(TaskQueue cookQueue) {
		this.client = new Person();
	}
	
	public PersonRunnable(TaskQueue cookQueue, int num) {
			this.client = new Person(num);
	}
	
	@Override
	public void run() {
		this.cookQueue.put(this.client.createTask());

	}
}
