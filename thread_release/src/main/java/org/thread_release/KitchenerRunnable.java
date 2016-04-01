package org.thread_release;

import org.printing_module.Kitchener;
import org.printing_module.Task;

public class KitchenerRunnable implements Runnable {

	private TaskQueue cookQueue;
	private Kitchener cooker;
	private int totalClient;
	private int cookTime;

	public KitchenerRunnable(TaskQueue cookQueue, int totalClient, int cookTime) {
		this.cooker = new Kitchener();
		this.cookQueue = cookQueue;
		this.totalClient = totalClient;
		this.cookTime = cookTime;
	}

	@Override
	public void run() {
		for (int k = 0; k < totalClient; k++) {

			Task next = cookQueue.next();

			while (next == null) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.err.println("Ошибка sleep KitchenerRunnable(ожидания заказа): " + e);
				}
				next = cookQueue.next();
			}

			if (cookQueue.checkStockIngredients(next) == false) {
				cookQueue.minusIngredients(next);
				try {
					Thread.sleep(this.cookTime);
				} catch (InterruptedException e) {
					System.err.println("Ошибка sleep KitchenerRunnable(приготовления заказа): " + e);
				}
				System.out.println("Порядковый номер заказа: " + (k + 1) + ". Заказ готов");
			}

			this.cooker.cook(next);
		}

	}
}
