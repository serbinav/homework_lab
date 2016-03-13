package org.thread_release;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.printing_module.Kitchener;
import org.printing_module.Person;
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
}

public class TestThread {
	public static void main(String[] args) {
		
		TaskQueue cookQueue = new TaskQueue();
		
		Kitchener cooker = new Kitchener();
		
		Person client = new Person();
		Thread cookThread = new Thread();
		cookThread.start();
		
	}
}
