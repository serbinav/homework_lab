package org.printing_module;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Person {
	private int number;
	private String namePerson;
	private static Random randomIn;
	private String[] partOne;
	private String[] partTwo;

	public Person(int number) {
		partOne = new String[41];
		partOne[0] = "Арт";
		partOne[1] = "Фолл";
		partOne[2] = "Ка";
		partOne[3] = "Миу";
		partOne[4] = "Бик";
		partOne[5] = "Мик";
		partOne[6] = "Синд";
		partOne[7] = "Воль";
		partOne[8] = "Рей";
		partOne[9] = "Хал";
		partOne[10] = "Роб";
		partOne[11] = "Тимм";
		partOne[12] = "Бокас";
		partOne[13] = "Асило";
		partOne[14] = "Зорт";
		partOne[15] = "Ногли";
		partOne[16] = "Седи";
		partOne[17] = "Верполь";
		partOne[18] = "Динреп";
		partOne[19] = "Гольд";
		partOne[20] = "Шайн";
		partOne[21] = "Каплон";
		partOne[22] = "Верило";
		partOne[23] = "Бесид";
		partOne[24] = "Хаге";
		partOne[25] = "Цунполь";
		partOne[26] = "Дерайтер";
		partOne[27] = "Саван";
		partOne[28] = "Пейсидон";
		partOne[29] = "Мосвад";
		partOne[30] = "Фифи";
		partOne[31] = "Серфиген";
		partOne[32] = "Сервеган";
		partOne[33] = "Луни";
		partOne[34] = "Джикполь";
		partOne[35] = "Квин";
		partOne[36] = "Рой";
		partOne[37] = "Десе";
		partOne[38] = "Верти";
		partOne[39] = "Водсаф";
		partOne[40] = "Ланеп";

		partTwo = new String[41];
		partTwo[0] = "пил";
		partTwo[1] = "ренайт";
		partTwo[2] = "ра";
		partTwo[3] = "хун";
		partTwo[4] = "десвер";
		partTwo[5] = "сертоплак";
		partTwo[6] = "вертино";
		partTwo[7] = "калап";
		partTwo[8] = "джани";
		partTwo[9] = "нипфит";
		partTwo[10] = "арагиппо";
		partTwo[11] = "гафваб";
		partTwo[12] = "килор";
		partTwo[13] = "занио";
		partTwo[14] = "васио";
		partTwo[15] = "слин";
		partTwo[16] = "тил";
		partTwo[17] = "хустил";
		partTwo[18] = "лисдо";
		partTwo[19] = "весдон";
		partTwo[20] = "верджин";
		partTwo[21] = "крион";
		partTwo[22] = "кит";
		partTwo[23] = "макс";
		partTwo[24] = "волк";
		partTwo[25] = "заредо";
		partTwo[26] = "кибило";
		partTwo[27] = "вемеп";
		partTwo[28] = "йетер";
		partTwo[29] = "вилькан";
		partTwo[30] = "ликасио";
		partTwo[31] = "дил";
		partTwo[32] = "лин";
		partTwo[33] = "сверн";
		partTwo[34] = "илип";
		partTwo[35] = "хасад";
		partTwo[36] = "серион";
		partTwo[37] = "саверн";
		partTwo[38] = "тероп";
		partTwo[39] = "санио";
		partTwo[40] = "пилион";
		
		randomIn = new Random();
		this.number = number;
		this.namePerson = this.randomName();

	}

	public int getNumber() {
		return number;
	}

	public String getNamePerson() {
		return this.namePerson;
	}

	public Task createTask() {

		Task newPizza = new Task(this);

		newPizza.setSize(randomIn.nextInt(10) + 1);

		List<Ingredient> component = new ArrayList<>();

		component.add(new Ingredient("сыр", randomIn.nextInt(10)));
		component.add(new Ingredient("колбаса", randomIn.nextInt(10)));
		component.add(new Ingredient("перец", randomIn.nextInt(10)));

		newPizza.setListIngredients(component);

		return newPizza;
	}

	// ----------------------------------Random--------------------------------------------
	private String randomName() {
		StringBuilder msg = new StringBuilder();
		msg.append(partOne[randomIn.nextInt(partOne.length)]);
		msg.append(partTwo[randomIn.nextInt(partTwo.length)]);
		return msg.toString();
	}
}