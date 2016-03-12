package org.thread_release;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.printing_module.Kitchener;

import org.printing_module.Task;

class TasQueue 
{ 
	private List<Task> tasks = new LinkedList<>();

	public List<Task> getTasks() {
		return Collections.unmodifiableList(tasks);
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	public int size() {
		return tasks.size();
	}
}

public class Test {
	public static void main(String[] args) {
		
		TasQueue cookQueue = new TasQueue();
		
		Kitchener cooker = new Kitchener();
		
		Thread cookThread = new Thread();

	}
}
