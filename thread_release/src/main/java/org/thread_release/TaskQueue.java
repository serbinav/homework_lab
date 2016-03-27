package org.thread_release;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.printing_module.Task;

public class TaskQueue 
{ 
	private Lock lock = new ReentrantLock();
	
	private final List<Task> tasks = new LinkedList<>();
	
	public Task next() {
		Task tmpTask;
		lock.lock();
		try {
			if (tasks.size() == 0) {
				return null;
			}
			tmpTask = tasks.get(0);
			tasks.remove(0);
			return tmpTask;			
		} finally {
			lock.unlock();
		}
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
	
	public Task get(int elem) {
		Task tmpTask;
		lock.lock();
		try {
			tmpTask = tasks.get(elem);
		} finally {
			lock.unlock();
		}
		return tmpTask;
	}
}
