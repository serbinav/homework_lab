package org.thread_release;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.console_release.StorageInList;
import org.printing_module.Ingredient;
import org.printing_module.Kitchener;
import org.printing_module.Person;
import org.printing_module.Pizza;
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
		return tasks.size();
	}
	
	public boolean isVoid() {
		return tasks.isEmpty();
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
		Kitchener cooker = new Kitchener();
		Person client = new Person();
		Runnable KitchenerRunnable = new KitchenerRunnable(cookQueue,newStorage);
		Thread cookThread = new Thread(KitchenerRunnable);
		cookThread.start();
	}
}
