package org.thread_release;

import java.util.List;

import org.printing_module.Ingredient;
import org.printing_module.Kitchener;
import org.printing_module.Storage;
import org.printing_module.Task;

public class KitchenerRunnable implements Runnable{
	
	private TaskQueue cookQueue;
	private Kitchener cooker;
	private Storage newStorage;
	private int cookTime;
	
	public KitchenerRunnable(TaskQueue cookQueue, Storage newStorage, int cookTime) {
		this.cooker = new Kitchener();
		this.cookQueue = cookQueue;
		this.newStorage = newStorage;
		this.cookTime = cookTime;
	}
	
	public boolean CheckStockIngredients(Task next) {
		boolean flagError = false;
		for (int q = 0; q < next.getListIngredients().size(); q++) {
			String ingrName = next.getListIngredients().get(q).getName();
			int ingrNumber = next.getListIngredients().get(q).getNumber();
			
			int ingrNumberStorage = this.newStorage.getCountByName(ingrName);
			
			if (ingrNumberStorage < ingrNumber) {
				System.err.println("Ошибка: недостатоно ингридиентов для обработки заказа");
				flagError = true;
				break;
			}
		}
		return flagError;
	}
	
	public void MinusIngredients(Task next) {
		for (int q = 0; q < next.getListIngredients().size(); q++) {
			String ingrName = next.getListIngredients().get(q).getName();
			int ingrNumber = next.getListIngredients().get(q).getNumber();
			
			int n = this.newStorage.getIndexByName(ingrName);
			List<Ingredient> tempList = this.newStorage.getListComponent();
			tempList.get(n).setNumber(this.newStorage.getCountByName(ingrName) - ingrNumber);
			this.newStorage.setListComponent(tempList);
		}
	}

	@Override
	public void run() {
		for (int k = 0; k < 5; k++) {
			
			System.out.println("cookQueue.size()" + cookQueue.size());
		
			Task next = cookQueue.next();

			while (next == null) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.err.println("Ошибка sleep: " + e);
				}
				next = cookQueue.next();
			}
			
			if (this.CheckStockIngredients(next) == false) {
					this.MinusIngredients(next);
				
				try {
					Thread.sleep(this.cookTime);
				} catch (InterruptedException e) {
					System.err.println("Ошибка sleep: " + e);
				}
				System.out.println(cookQueue.size());
				System.out.println(next.printTask());
				System.out.println(next.getClient().getNamePerson());
			}

			this.cooker.cook(next, this.newStorage);
		}
		
	}
}
