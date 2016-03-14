package org.thread_release;

import org.printing_module.Kitchener;
import org.printing_module.Person;
import org.printing_module.Storage;

public class KitchenerRunnable implements Runnable{
	
	TaskQueue cookQueue;
	Kitchener cooker;
	Person client;
	Storage newStorage;

	public KitchenerRunnable(TaskQueue cookQueue, Storage newStorage) {
		this.cooker = new Kitchener();
		this.client = new Person();
		this.cookQueue = cookQueue;
		this.newStorage = newStorage;
		
		this.cookQueue.put(this.client.createTask());
	}

	@Override
	public void run() {
		while (!cookQueue.isVoid()) {
	            this.cooker.cook(cookQueue.next(),this.newStorage);   
	    }
	}
}
