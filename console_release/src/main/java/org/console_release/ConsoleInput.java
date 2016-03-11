package org.console_release;

import org.printing_module.Printing;

//Реализация интерфейса выводящая переданный параметром текст в консоль
public class ConsoleInput implements Printing {

	@Override
	public void printSomething(String input) {
		System.out.println(input);
	}
}
