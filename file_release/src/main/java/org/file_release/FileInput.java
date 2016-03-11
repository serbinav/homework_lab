package org.file_release;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.printing_module.Printing;

//Реализация интерфейса выводящая переданный параметром текст в файл (log.txt)
public class FileInput implements Printing {
	private String MY_CONSTANT_FILE = "log.txt";

	@Override
	public void printSomething(String input) {
		try (BufferedWriter newHtml = new BufferedWriter(new FileWriter(MY_CONSTANT_FILE))) {
			newHtml.write(input);
		} catch (IOException e) {
			System.err.println("ошибка: ввода вывода при создании файла " + MY_CONSTANT_FILE + "!");
		}
	}
}
