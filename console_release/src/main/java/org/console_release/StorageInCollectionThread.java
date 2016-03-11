package org.console_release;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.printing_module.Ingredient;
import org.printing_module.Pizza;
import org.printing_module.ProductionPizza;
import org.printing_module.Storage;

public class StorageInCollectionThread implements ProductionPizza {

	private static final int GUINNESS_CONSTANT = 4100;
	private static final int UPPER_RANGE_CONSTANT = 2147483647;
	
	private static Random randomIn;
	private static final long cookTime = 1000; //millis

	public StorageInCollectionThread() {
		randomIn = new Random();
	}

	@Override
	public int selectPizzaSize() {
		System.out.println("Введите размера пиццы: ");

		int sizePizza = randomInt();

		if (sizePizza <= 0) {
			System.err.println("Вы ввели ноль или отрицательное число, попробуйте повторить ввод еще раз! ");
		} else if (sizePizza == UPPER_RANGE_CONSTANT) {
		} else if (sizePizza > GUINNESS_CONSTANT) {
			System.err.println("У нас пиццу размером больше, чем рекорд из Книги рекордов Гиннесса(" + GUINNESS_CONSTANT
					+ ") не пекут, повторите ввод еще раз ");
		}
		return sizePizza;
	}

	@Override
	public List<Ingredient> chooseIngredientForPizza(Storage hangar) {
		List<Ingredient> component = new ArrayList<>();

		System.out.println(
				"Вводите ингредиенты пиццы (чтобы завершить ввод введите вместо названия ингридиента введите - стоп )");
		for (int r = 0; r < 3; ++r){

			System.out.println(hangar.printListComponent());

			System.out.println("Введите название ингредиента");
			String ingrName = randomIngredient();
			int ingrNumberStorage = hangar.getCountByName(ingrName);

			while (ingrNumberStorage == -1 || ingrNumberStorage == 0) {
				if (ingrName.compareTo("?") == 0 || ingrName.compareTo("help") == 0) {
					printHelp();
				} else if (ingrName.compareTo("стоп") != 0) {
					System.err.println("Вы ввели ингредиент которого нет на сладе, повторите ввод еще раз ");
				} else {
					return component;
				}
				ingrName = randomIngredient();
				ingrNumberStorage = hangar.getCountByName(ingrName);
			}

			System.out.println("Введите количество ингредиента ");
			System.out.println("Данного ингридиента на складе осталось " + ingrNumberStorage);

			int ingrNumber = randomInt();

			while (ingrNumberStorage < ingrNumber || ingrNumber == UPPER_RANGE_CONSTANT) {
				if (ingrNumber == UPPER_RANGE_CONSTANT) {
					ingrNumber = randomInt();
				} else {
					System.err.println(
							"Вы ввели ингредиент  количества которого не хватает для приготовления пиццы, повторите ввод еще раз ");
					ingrNumber = randomInt();
				}
			}

			if (ingrNumber < 0) {
				boolean flagRemove = false;
				for (int j = 0; j < component.size(); j++) {
					if (component.get(j).getName().compareTo(ingrName) == 0) {
						if (component.get(j).getNumber() < Math.abs(ingrNumber)) {
							System.err.println(
									"Вы пытаетесь убавить количество ингредиента больше чем его есть в пицце, повторите ввод еще раз ");
							break;
						} else {
							component.get(j).setNumber(component.get(j).getNumber() + ingrNumber);
							int n = hangar.getIndexByName(ingrName);
							hangar.getListComponent().get(n).setNumber(hangar.getCountByName(ingrName) - ingrNumber);

							if (component.get(j).getNumber() == 0) {
								component.remove(j);
							}

							flagRemove = true;
							break;
						}
					}
				}

				if (flagRemove == false) {
					System.err.println(
							"Вы пытаетесь убавить количество ингредиента который не добавлен в пиццу, повторите ввод еще раз ");
				}
			} else {
				int numberElem = hangar.getIndexByName(ingrName);

				hangar.getListComponent().get(numberElem).setNumber(ingrNumberStorage - ingrNumber);

				boolean flagAdd = false;
				for (int i = 0; i < component.size(); i++) {
					if (component.get(i).getName().compareTo(ingrName) == 0) {
						component.get(i).setNumber(component.get(i).getNumber() + ingrNumber);
						flagAdd = true;
						break;
					}
				}

				if (flagAdd == false) {
					component.add(new Ingredient(ingrName, ingrNumber));
				}
			}
		}
		//!!!
		return component;
	}

	@Override
	public void printOrderPizza(Pizza input) {
		System.out.println(input.printPizza());
	}

	@Override
	public void printHelp() {
		System.err.println("Структура программы: " + "Задать размер пиццы, "
				+ "задать название ингридиентов для пиццы и их количесво, "
				+ "распечатать состав и размер заказанной пиццы, " + "а теперь продолжайте ввод данных! ");
	}

	// ----------------------------------Random--------------------------------------------
	public int randomInt() {
		int number = 0;
		number = randomIn.nextInt(10);
		System.out.println(number);
		return number;
	}
	
	public String randomIngredient() {
		int number = 0;
		number = randomIn.nextInt(3)+1;
	
		if (number == 1) {
			System.out.println("сыр");
			return "сыр";
		} else if (number == 2) {
			System.out.println("колбаса");
			return "колбаса";
		} else{
			System.out.println("перец");
			return "перец";	
		}
	}
	
	// ----------------------------------Thread--------------------------------------------

}

