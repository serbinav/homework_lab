package org.console_app;

import org.console_release.ConsoleInput;
import org.file_release.FileInput;
import org.printing_module.Printing;

//Консольное приложение, которое принимает два аргумента командной строки
//Первый - целочисленный, второй - строковой
//В зависимости от значения первого аргумента (0 - консоль, 1 - файл) 
//Выводит переданный вторым аргументом текст с помощью одной из реализаций интерфейса
public class AppTest {

	public boolean testArray(String[] args) {
		if (args.length == 0) {
			System.err.println("ошибка: не заданы входные параметры");
			return false;
		}

		if (args.length < 2) {
			System.err.println("ошибка: входных параметров должно быть не меньше двух");
			return false;
		}
		return true;
	}

	public boolean testInt(String numberString) {
		int numberLine = 0;

		try {
			numberLine = new Integer(numberString);

			if (numberLine > 1) {
				System.err.println("ошибка: первый параметр может быть равен только 0 или 1");
				return false;
			}
		} catch (NumberFormatException e) {
			System.err.println("ошибка: первый параметр не целочисленный");
			return false;
		}
		return true;
	}

	public String concatStr(String[] args) {
		StringBuilder body = new StringBuilder();
		for (int i = 1; i < args.length; i++) {
			body.append(args[i] + " ");
		}
		return body.toString();
	}

	public static void main(String[] args) {
		AppTest app = new AppTest();
		boolean error;
		error = app.testArray(args);

		if (error == false) {
			return;
		}

		error = app.testInt(args[0]);
		if (error == false) {
			return;
		}

		if ("1".equals(args[0])) {
			Printing two = new FileInput();
			two.printSomething(app.concatStr(args));
		} else {
			Printing once = new ConsoleInput();
			once.printSomething(app.concatStr(args));
		}
	}
}
