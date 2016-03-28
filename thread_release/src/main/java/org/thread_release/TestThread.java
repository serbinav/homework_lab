package org.thread_release;

import java.util.ArrayList;
import java.util.List;

import org.console_release.StorageInList;
import org.printing_module.Ingredient;
import org.printing_module.Storage;

public class TestThread {
	private static final int COOK_TIME = 1000;

	public boolean testArray(String[] args) {
		if (args.length == 0) {
			System.err.println("ошибка: не задан входной параметр");
			return false;
		}

		if (args.length > 1) {
			System.err.println("ошибка: входной параметр должен быть один");
			return false;
		}
		return true;
	}

	public int convertInt(String numberString) {
		int numberLine = 1;

		try {
			numberLine = new Integer(numberString);

			if (numberLine < 0) {
				System.err.println("ошибка: параметр не может быть отрицательным");
				return -1;
			}

		} catch (NumberFormatException e) {
			System.err.println("ошибка: параметр не целочисленный");
			return -1;
		}
		return numberLine;
	}

	public static void main(String[] args) {
		TestThread app = new TestThread();

		if (!app.testArray(args)) {
			return;
		}
		int totalClient = app.convertInt(args[0]);
		if (totalClient == -1) {
			return;
		}

		Storage newStorage = new StorageInList();
		List<Ingredient> listComponent = new ArrayList<>();

		listComponent.add(new Ingredient("сыр", 100));
		listComponent.add(new Ingredient("колбаса", 100));
		listComponent.add(new Ingredient("перец", 100));
		newStorage.setListComponent(listComponent);

		TaskQueue cookQueue = new TaskQueue(newStorage);
		Runnable kitchenerRunnable = new KitchenerRunnable(cookQueue, newStorage, totalClient, COOK_TIME);

		for (int c = 0; c < totalClient; c++) {
			Runnable personRunnable = new PersonRunnable(cookQueue, c + 1);
			Thread clientThread = new Thread(personRunnable);
			clientThread.start();	
		}

		Thread cookThread = new Thread(kitchenerRunnable);
		cookThread.start();

		try {
			cookThread.join();
		} catch (InterruptedException e) {
			System.err.println("Ошибка: " + e);
		}
	}
}
