package org.thread_release;

import java.util.ArrayList;
import java.util.List;

import org.console_release.StorageInList;
import org.printing_module.Ingredient;
import org.printing_module.Storage;

public class TestThread {
	private static final int cookTime = 1000;

	public static void main(String[] args) {
		Storage newStorage = new StorageInList();
		List<Ingredient> listComponent = new ArrayList<>();

		listComponent.add(new Ingredient("сыр", 100));
		listComponent.add(new Ingredient("колбаса", 100));
		listComponent.add(new Ingredient("перец", 100));
		newStorage.setListComponent(listComponent);
		
		TaskQueue cookQueue = new TaskQueue();
		Runnable KitchenerRunnable = new KitchenerRunnable(cookQueue,newStorage,cookTime);
		Runnable PersonRunnable = new PersonRunnable(cookQueue);

		for (int c = 0; c < 5; c++) {
			Thread clientThread = new Thread(PersonRunnable);
			clientThread.start();
		}

		Thread cookThread = new Thread(KitchenerRunnable);
		cookThread.start();
		
		try {
			cookThread.join();
		} catch (InterruptedException e) {
			System.err.println("Ошибка: " + e);
		}
	}
}
