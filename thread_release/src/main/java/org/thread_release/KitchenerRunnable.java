package org.thread_release;

import org.printing_module.Kitchener;
import org.printing_module.Storage;

public class KitchenerRunnable implements Runnable{
	
	TaskQueue cookQueue;
	Kitchener cooker;
	Storage newStorage;

	public KitchenerRunnable(TaskQueue cookQueue, Storage newStorage) {
		this.cooker = new Kitchener();
		this.cookQueue = cookQueue;
		this.newStorage = newStorage;
	}

	@Override
	public void run() {
		while (!cookQueue.isVoid()) {
	            this.cooker.cook(cookQueue.next(),this.newStorage);   
	    }
	}
}
