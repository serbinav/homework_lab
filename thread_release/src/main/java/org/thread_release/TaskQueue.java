package org.thread_release;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.printing_module.Task;

public class TaskQueue 
{ 
	private Lock lock = new ReentrantLock();
	
	private final Queue<Task> tasks = new LinkedList<>();

	public Task next() {
		Task tmpTask;
		lock.lock();
	    try{
			tmpTask = tasks.poll();
	    }
	    finally{
			lock.unlock();
	    }
		return tmpTask; 
	}
	        
	public void put(Task task) {
		lock.lock();
	    try{
			tasks.add(task);
	    }
	    finally{
			lock.unlock();
	    }
	}

	public int size() {
		int format;
		lock.lock();
		try {
			format = tasks.size();	
		} finally {
			lock.unlock();
		}
		return format;
	}
}
