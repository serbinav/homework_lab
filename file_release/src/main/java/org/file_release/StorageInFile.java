package org.file_release;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.printing_module.Ingredient;
import org.printing_module.Storage;

public class StorageInFile implements Storage, Serializable{
	private static final long serialVersionUID = -7630747103465206271L;
	
	private List<Ingredient> listComponent;
	
	private static final String MY_CONST_PATH = "serializedStorage";//C:\\WORK\\***.serial
	private File targetFile;

	public StorageInFile() {
		this.targetFile = new File(MY_CONST_PATH);
		this.listComponent = new ArrayList<>();

		if (!targetFile.exists()) {
			System.err.println(
					"файл, в котором хранятся ингридиенты находящиеся на складе не найден, попытаемся его создать");
			createNewStorageFile();
		} else {
			System.out.println("файл, в котором хранятся ингридиенты находящиеся на складе найден!");
			try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(targetFile))){
				if (deserializeList(input.readObject()) == -1) {
					targetFile.delete();

					createNewStorageFile();
				}	
				else{
					System.out.println("на складе хранятся:");

					for (Ingredient s : this.listComponent) {
						System.out.println(s.getName() + " - " + s.getNumber() + " едениц");
					}
				}
			} catch (IOException | ClassNotFoundException e) {
				System.err.println("ошибка ввода вывода при чтении файла: " + e);
				targetFile.delete();

				createNewStorageFile();
			}
		}
	}

	@Override
	public List<Ingredient> getListComponent() {
		return Collections.unmodifiableList(listComponent);
	}

	@Override
	public void setListComponent(List<Ingredient> listComponent) {
		if(this.listComponent.size() == 0){
			this.listComponent = listComponent;	
		}
		save();
	}

	@Override
	public int getCountByName(String name) {
		for (Ingredient ingredient : listComponent) {
			if (ingredient.getName().compareTo(name) == 0) {
				return ingredient.getNumber();
			}
		}
		return -1;
	}

	@Override
	public int getIndexByName(String name) {
		for (int i = 0; i < listComponent.size(); i++) {
			if (listComponent.get(i).getName().compareTo(name) == 0) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public String printListComponent() {
		StringBuilder output = new StringBuilder();
		output.append("На складе имеется: ");
		for (Ingredient ingredient : listComponent) {
			output.append(ingredient.getName());
			output.append(" ");
			output.append(ingredient.getNumber());
			output.append(" ");
		}
		return output.toString();
	}
	
	// сохранение данных
	public int save() {
		System.out.println("cохранение данных");
		if (!targetFile.exists()) {
			System.err.println("файл, в котором хранятся ингридиенты находящиеся на складе не найден, попытаемся его создать");
			return createNewStorageFile();
		}else {
			System.out.println("файл, в котором хранятся ингридиенты находящиеся на складе найден!");
			return serializeList(listComponent);
		}
	}
	
	// ----------------------------------Serializable--------------------------------------------
	public int serializeList(List<Ingredient> input) {
		System.out.println("попытаемся записать данные в файл");
		try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(targetFile))) {
			stream.writeObject(input);
			stream.flush();
			System.out.println("запись данных в файл прошла успешно!");
		} catch (IOException e) {
			System.err.println("ошибка ввода вывода при записи данных в файл: " +  e);
			return -1;
		}
		return 0;
	}

	public int createNewStorageFile() {
	 try {
			targetFile.createNewFile();
			System.out.println("создание файла прошло успешно!");			
			return serializeList(listComponent);
		} catch (IOException e) {
			System.err.println("ошибка ввода вывода при создании файла: " +  e);
			return -1;
		}
	}
	
	public int deserializeList(Object input) {
		Object tempObject = input;
		if (tempObject instanceof List<?>) {
			List<?> tempList = (List<?>) tempObject;
			if (tempList.size() > 0) {
				for (int i = 0; i < tempList.size(); i++) {
					Object tmpIngr = tempList.get(i);
					if (tmpIngr instanceof Ingredient) {
						Ingredient ser = (Ingredient) tmpIngr;
						this.listComponent.add(ser);
					} else {
						System.err.println(
								"ошибка серилизации ингридиента в списке ингридиентов при чтении файла ");
						return -1;
					}
				}
				return 0;
			} else {
				System.err.println("ошибка серилизации списка ингридиентов при чтении файла ");
				return -1;
			}
		} else {
			System.err.println("ошибка серилизации списка ингридиентов при чтении файла ");
			return -1;
		}
	}
}
