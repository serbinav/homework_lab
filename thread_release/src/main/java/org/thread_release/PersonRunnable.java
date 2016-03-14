package org.thread_release;

import org.printing_module.Person;

public class PersonRunnable implements Runnable{
	
	TaskQueue cookQueue;
	Person client;
	
	public PersonRunnable(TaskQueue cookQueue) {
			this.client = new Person();
	}
	
	@Override
	public void run() {
		this.cookQueue.put(this.client.createTask());

	}

}
