package org.thread_release;

import org.printing_module.Kitchener;

public class KitchenerRunnable implements Runnable{
	
	TaskQueue cookQueue;
	Kitchener cooker;

	public KitchenerRunnable(Kitchener cooker, TaskQueue cookQueue) {
		this.cooker = cooker;
		this.cookQueue = cookQueue;
	}

	@Override
	public void run() {
		while (!cookQueue.isVoid()) {
	            cookQueue.next(); 
	    }
	}
}
