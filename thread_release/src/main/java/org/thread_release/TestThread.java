package org.thread_release;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.console_release.StorageInList;
import org.printing_module.Ingredient;
import org.printing_module.Storage;
import org.printing_module.Task;

class TaskQueue 
{ 
	private Lock lock = new ReentrantLock();
	
	private final Queue<Task> tasks = new LinkedList<>();

	public Task next() {
		lock.lock();
		Task tmpTask = tasks.poll();
		lock.unlock();
		return tmpTask; 
	}
	        
	public void put(Task task) {
		lock.lock();
		tasks.add(task);
		lock.unlock();
	}

	public int size() {
		lock.lock();
		int format = tasks.size();
		lock.unlock();
		return format;
	}
	
	public boolean isVoid() {
		lock.lock();
		boolean check = tasks.isEmpty();
		lock.unlock();
		return check;
	}
}

public class TestThread {
	public static void main(String[] args) {
		
		Storage newStorage = new StorageInList();
		List<Ingredient> listComponent = new ArrayList<>();

		listComponent.add(new Ingredient("сыр", 100));
		listComponent.add(new Ingredient("колбаса", 100));
		listComponent.add(new Ingredient("перец", 100));
		newStorage.setListComponent(listComponent);
		
		TaskQueue cookQueue = new TaskQueue();
		Runnable KitchenerRunnable = new KitchenerRunnable(cookQueue,newStorage);
		Runnable PersonRunnable = new PersonRunnable(cookQueue);

		for (int c = 0; c < 5; c++) {
			Thread clientThread = new Thread(PersonRunnable);
			clientThread.start();
		}

		Thread cookThread = new Thread(KitchenerRunnable);
		cookThread.start();
	}
}
