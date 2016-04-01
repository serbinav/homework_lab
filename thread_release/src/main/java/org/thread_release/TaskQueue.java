package org.thread_release;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.printing_module.Ingredient;
import org.printing_module.Storage;
import org.printing_module.Task;

public class TaskQueue {
	private Lock lock = new ReentrantLock();
	private final List<Task> tasks = new LinkedList<>();
	private int globalSize = 0;
	private Storage newStorage;

	public TaskQueue(Storage newStorage) {
		this.newStorage = newStorage;
	}
	
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

	/*
	* Поступил заказ № 1 от Рейверджин(Пицца размером: 8. Состоящая из: сыр - 5, колбаса - 7, перец - 1)
	* очередь заказов:  клиент № 2 - Рейверджин 
    * На складе осталось: сыр - 100, колбаса - 100, перец - 100
    * Немного был откорректирован вывод
    * например здесь Рейверджин это один и тот же человек, 
    * заказ из очереди забирается не моментально, а с небольшой задержкой
	*/
	public void put(Task task) {
		lock.lock();
		try {
			tasks.add(task);
			globalSize++;

			System.out.println("Поступил заказ № " + this.globalSize + " от " + task.getClient().getNamePerson() + "("
					+ task.printTask() + ")");
			StringBuilder tmpString = new StringBuilder();
			if (tasks.size() > 0) {
				for (int i = 0; i < tasks.size(); i++) {
					tmpString.append(" клиент № " + tasks.get(i).getClient().getNumber() + " - "
							+ tasks.get(i).getClient().getNamePerson() + " ");
				}
				System.out.println("очередь заказов: " + tmpString.toString());
			}
			System.out.println(newStorage.printListComponent());
			System.out.println();
		} finally {
			lock.unlock();
		}
	}
	
/*	- поступил заказ №1 от клиента №1 (состав:…); очередь заказов:…,склад:...

	- поступил заказ №2 от клиента №3 (состав:…); очередь заказов:…,склад:...

	- поступил заказ №3 от клиента №5 (состав:…); очередь заказов:…,склад:...

	- заказ №1 готов.

	- поступил заказ №4 от клиента №2 (состав:…); очередь заказов:…,склад:...

	- поступил заказ №5 от клиента №1 (состав:…); очередь заказов:…,склад:...

	- заказ №2 готов.

	- заказ №3 готов.*/

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
	
	/*
	* так как к "складу" нужен доступ из "клиента"(по заданию),
	* а "повар" тоже должен работать со складом,
	* то нужно использовать общую блокировку,
	* поэтому checkStockIngredients() и minusIngredients()
	* переносятся в очередь
	*/
	public boolean checkStockIngredients(Task next) {
		lock.lock();
		try {
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
		} finally {
			lock.unlock();
		}
		return false;
	}

	public void minusIngredients(Task next) {
		lock.lock();
		try {
			List<Ingredient> tmpList = next.getListIngredients();

			for (int q = 0; q < tmpList.size(); q++) {
				String ingrName = tmpList.get(q).getName();
				int ingrNumber = tmpList.get(q).getNumber();

				int n = this.newStorage.getIndexByName(ingrName);
				List<Ingredient> tempList = this.newStorage.getListComponent();
				tempList.get(n).setNumber(this.newStorage.getCountByName(ingrName) - ingrNumber);
				this.newStorage.setListComponent(tempList);
			}
			
			System.out.println("Повар забрал на приготовление заказ № " + this.globalSize);
			System.out.println();
			
		} finally {
			lock.unlock();
		}
	}
}
