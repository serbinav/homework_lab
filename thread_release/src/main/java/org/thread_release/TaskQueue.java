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
					tmpString.append(" № " + tasks.get(i).getClient().getNumber() + " - "
							+ tasks.get(i).getClient().getNamePerson() + " ");
				}
				System.out.println("очередь заказов: " + tmpString.toString());
			}
			System.out.println("На складе осталось: " + newStorage.printListComponent());
		} finally {
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
		} finally {
			lock.unlock();
		}
	}
}
