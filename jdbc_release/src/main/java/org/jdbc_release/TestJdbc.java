package org.jdbc_release;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.ds.PGPoolingDataSource;

	//Использовать транзакции
	//Обновление количества ингредиентов должно изменяться в БД

public class TestJdbc {

	//private static final String DB_SERVER_NAME = "jdbc:postgresql://127.0.0.1";
	private static final String DB_SERVER_NAME = "localhost";
	private static final int    DB_PORT = 5432;
	private static final String DB_NAME = "test";
	private static final String DB_USER = "postgres";
	private static final String DB_PASSWORD = "12345678";
	
	public static void main(String[] args) {

		PGPoolingDataSource ds = new PGPoolingDataSource();
		ds.setServerName(DB_SERVER_NAME);
		ds.setPortNumber(DB_PORT);
		ds.setDatabaseName(DB_NAME);
		ds.setUser(DB_USER);
		ds.setPassword(DB_PASSWORD);
		ds.setMaxConnections(100);
		ds.setInitialConnections(20);

		// SELECT name, number_ingr FROM storage,ingredient_dict
		// where storage.id_ingr = ingredient_dict.id;

		// INSERT INTO pizza(size, id_ingr, number_ingr) VALUES (7, 3, 50);

		// INSERT INTO storage(id_ingr, number_ingr) VALUES (3, 100);

	
		// Создаём соединение
		try (Connection connection = ds.getConnection()) {
			System.out.println("Соединение установлено");

			// Для использования SQL запросов существуют 3 типа объектов:
			// 1.Statement: используется для простых случаев без параметров
			Statement statement = null;

			statement = connection.createStatement();
			// Выполним запрос

			//"select sign(count(*)) from storage"
			ResultSet result1 = statement.executeQuery("select sign(count(*)) from storage");
			// result это указатель на первую строку с выборки
			// чтобы вывести данные мы будем использовать
			// метод next() , с помощью которого переходим к следующему элементу
			System.out.println("Выводим statement");
				System.out.println(result1.next());
			
			
			ResultSet result2 = statement.executeQuery("select sign(count(*)) test_table");
			// result это указатель на первую строку с выборки
			// чтобы вывести данные мы будем использовать
			// метод next() , с помощью которого переходим к следующему элементу
			System.out.println("Выводим statement");
				System.out.println(result1.next());
			
			
//			System.out.println("Номер в выборке #" + result1.getRow() + "\t Количество в базе #"
//					+ result1.getInt("id_ingr") + "\t" + result1.getString("id"));
			
			// Вставить запись
			/*
			 * statement.executeUpdate(
			 * "INSERT INTO ingredient(name) values('name')"); //Обновить запись
			 * statement.executeUpdate(
			 * "UPDATE ingredient SET name = 'admin' where id = 1");
			 */

			// 2.пример транзакции
			Statement st = connection.createStatement();
			connection.setAutoCommit(false);
			try {
				st.execute("INSERT INTO pizza(id_pizza, size, id_ingr, number_ingr) VALUES (1, 7, 3, 50)");
				st.executeUpdate("UPDATE storage SET number_ingr=number_ingr-50 WHERE id_ingr = 3");

				st.execute("INSERT INTO pizza(id_pizza, size, id_ingr, number_ingr) VALUES (1, 7, 1, 25)");
				st.executeUpdate("UPDATE storage SET number_ingr=number_ingr-25 WHERE id_ingr = 1");

				connection.commit(); // фиксируем транзакцию
			} catch (SQLException e) {
				// Если что-то пошло не так, откатываем всю транзакцию
				connection.rollback();
			}


		} catch (Exception ex) {
			// выводим наиболее значимые сообщения
			System.err.println(ex);
		}

	}
}
