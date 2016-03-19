package org.printing_module;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Person {
	private int number;
	private String namePerson;
	private static Random randomIn;

	public Person(int number) {
		randomIn = new Random();
		this.number = number;
		this.namePerson = this.randomName();
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getNamePerson() {
		return this.namePerson;
	}

	public void setNamePerson(String namePerson) {
		this.namePerson = namePerson;
	}

	public Task createTask() {

		Task newPizza = new Task();

		newPizza.setSize(randomInt(10) + 1);

		List<Ingredient> component = new ArrayList<>();

		component.add(new Ingredient("сыр", randomInt(10)));
		component.add(new Ingredient("колбаса", randomInt(10)));
		component.add(new Ingredient("перец", randomInt(10)));

		newPizza.setListIngredients(component);

		return newPizza;
	}

	// ----------------------------------Random--------------------------------------------
	public int randomInt(int upTo) {
		int number = 0;
		number = randomIn.nextInt(upTo);
		return number;
	}

	public String randomName() {
		String[] w3;
		w3 = new String[41];
		w3[0] = "Арт";
		w3[1] = "Фолл";
		w3[2] = "Ка";
		w3[3] = "Миу";
		w3[4] = "Бик";
		w3[5] = "Мик";
		w3[6] = "Синд";
		w3[7] = "Воль";
		w3[8] = "Рей";
		w3[9] = "Хал";
		w3[10] = "Роб";
		w3[11] = "Тимм";
		w3[12] = "Бокас";
		w3[13] = "Асило";
		w3[14] = "Зорт";
		w3[15] = "Ногли";
		w3[16] = "Седи";
		w3[17] = "Верполь";
		w3[18] = "Динреп";
		w3[19] = "Гольд";
		w3[20] = "Шайн";
		w3[21] = "Каплон";
		w3[22] = "Верило";
		w3[23] = "Бесид";
		w3[24] = "Хаге";
		w3[25] = "Цунполь";
		w3[26] = "Дерайтер";
		w3[27] = "Саван";
		w3[28] = "Пейсидон";
		w3[29] = "Мосвад";
		w3[30] = "Фифи";
		w3[31] = "Серфиген";
		w3[32] = "Сервеган";
		w3[33] = "Луни";
		w3[34] = "Джикполь";
		w3[35] = "Квин";
		w3[36] = "Рой";
		w3[37] = "Десе";
		w3[38] = "Верти";
		w3[39] = "Водсаф";
		w3[40] = "Ланеп";

		String[] w4;
		w4 = new String[41];
		w4[0] = "пил";
		w4[1] = "ренайт";
		w4[2] = "ра";
		w4[3] = "хун";
		w4[4] = "десвер";
		w4[5] = "сертоплак";
		w4[6] = "вертино";
		w4[7] = "калап";
		w4[8] = "джани";
		w4[9] = "нипфит";
		w4[10] = "арагиппо";
		w4[11] = "гафваб";
		w4[12] = "килор";
		w4[13] = "занио";
		w4[14] = "васио";
		w4[15] = "слин";
		w4[16] = "тил";
		w4[17] = "Хустил";
		w4[18] = "лисдо";
		w4[19] = "весдон";
		w4[20] = "верджин";
		w4[21] = "крион";
		w4[22] = "кит";
		w4[23] = "макс";
		w4[24] = "волк";
		w4[25] = "заредо";
		w4[26] = "кибило";
		w4[27] = "вемеп";
		w4[28] = "йетер";
		w4[29] = "вилькан";
		w4[30] = "ликасио";
		w4[31] = "дил";
		w4[32] = "лин";
		w4[33] = "сверн";
		w4[34] = "илип";
		w4[35] = "хасад";
		w4[36] = "серион";
		w4[37] = "саверн";
		w4[38] = "тероп";
		w4[39] = "санио";
		w4[40] = "пилион";

		StringBuilder msg = new StringBuilder();
		msg.append(w3[this.randomInt(40)]);
		msg.append(w4[this.randomInt(40)]);

		return msg.toString();
	}
}