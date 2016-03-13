package org.thread_release;

public class KitchenerRunnable implements Runnable{
	
	TaskQueue cookQueue;

	public KitchenerRunnable(TaskQueue cookQueue) {
		this.cookQueue = cookQueue;
	}

	@Override
	public void run() {
		while (!cookQueue.isVoid()) {
	            cookQueue.next(); 
	    }
	}
}
