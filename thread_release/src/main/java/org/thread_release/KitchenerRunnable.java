package org.thread_release;

import java.util.List;

import org.printing_module.Ingredient;
import org.printing_module.Kitchener;
import org.printing_module.Storage;
import org.printing_module.Task;

public class KitchenerRunnable implements Runnable {

	private TaskQueue cookQueue;
	private Kitchener cooker;
	private Storage newStorage;
	private int totalClient;
	private int cookTime;

	public KitchenerRunnable(TaskQueue cookQueue, Storage newStorage, int totalClient, int cookTime) {
		this.cooker = new Kitchener();
		this.cookQueue = cookQueue;
		this.newStorage = newStorage;
		this.totalClient = totalClient;
		this.cookTime = cookTime;
	}

	public boolean checkStockIngredients(Task next) {
		List<Ingredient> tmpList = next.getListIngredients();

		for (int q = 0; q < tmpList.size(); q++) {
			String ingrName = tmpList.get(q).getName();
			int ingrNumber = tmpList.get(q).getNumber();

			int ingrNumberStorage = this.newStorage.getCountByName(ingrName);

			if (ingrNumberStorage < ingrNumber) {
				System.err.println("Ошибка: недостатоно ингридиентов для обработки заказа");
				return true;
			}
		}
		return false;
	}

	public void minusIngredients(Task next) {
		List<Ingredient> tmpList = next.getListIngredients();

		for (int q = 0; q < tmpList.size(); q++) {
			String ingrName = tmpList.get(q).getName();
			int ingrNumber = tmpList.get(q).getNumber();

			int n = this.newStorage.getIndexByName(ingrName);
			List<Ingredient> tempList = this.newStorage.getListComponent();
			tempList.get(n).setNumber(this.newStorage.getCountByName(ingrName) - ingrNumber);
			this.newStorage.setListComponent(tempList);
		}
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

			if (this.checkStockIngredients(next) == false) {
				this.minusIngredients(next);

				try {
					Thread.sleep(this.cookTime);
				} catch (InterruptedException e) {
					System.err.println("Ошибка sleep KitchenerRunnable(приготовления заказа): " + e);
				}				
				
				System.out.println("Порядковый номер заказа: " + (k + 1) + ". Заказ готов");
			}

			this.cooker.cook(next, this.newStorage);
		}

	}
}
