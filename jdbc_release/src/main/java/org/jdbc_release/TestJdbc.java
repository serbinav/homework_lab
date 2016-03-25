package org.jdbc_release;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.ds.PGPoolingDataSource;
import org.printing_module.Person;
import org.printing_module.Task;

public class TestJdbc {

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

		try (Connection connection = ds.getConnection()) {
			System.out.println("Соединение с БД установлено");

			Statement statement = null;
			statement = connection.createStatement();
			ResultSet result1 = statement.executeQuery("select count(*) from storage");

			int count = 0;
			while (result1.next()) {
				count = result1.getInt("count");
			}

			if (count == 0) {
				System.err.println("Ошибка: склад пуст, ингридиенты будут добавлены");

				PreparedStatement preparedStatementInsert = null;
				preparedStatementInsert = connection.prepareStatement("INSERT INTO storage (id, id_ingr, number_ingr) VALUES (?, ?, ?)");
				
				connection.setAutoCommit(false);
				try {
					for (int i = 1; i < 4; i++) {
						preparedStatementInsert.setInt(1, i);
						preparedStatementInsert.setInt(2, i);
						preparedStatementInsert.setInt(3, 100);
						preparedStatementInsert.executeUpdate();
					}
					connection.commit();
				} catch (SQLException e) {
					connection.rollback();
					System.err.println("Ошибка при добавлении ингридиентов на склад");
				}
				connection.setAutoCommit(true);
			} else {
				System.out.println("На складе присутствуют запасы");
			}

			Person client;
			client = new Person(1);
			Task newTask = client.createTask();
			System.out.println("Сгенерировали заказ");
			newTask.setClient(client);

			boolean flagError = false;
			System.out.println("Проверям наличие ингридиентов на складе");
			for (int q = 0; q < newTask.getListIngredients().size(); q++) {
				String ingrName = newTask.getListIngredients().get(q).getName();
				int ingrNumber = newTask.getListIngredients().get(q).getNumber();

				PreparedStatement preparedStatement = null;
				preparedStatement = connection.prepareStatement("SELECT name, number_ingr FROM storage,ingredient_dict"
						+ " where storage.id_ingr = ingredient_dict.id and name = ?");

				preparedStatement.setString(1, ingrName);
				ResultSet result2 = preparedStatement.executeQuery();

				int ingrNumberStorage = 0;
				while (result2.next()) {
					ingrNumberStorage = result2.getInt("number_ingr");
				}

				if (ingrNumberStorage < ingrNumber) {
					System.err.println("Ошибка: недостаточно ингридиента: \"" + ingrName + "\" для обработки заказа");
					flagError = true;
					break;
				}
			}

			if (flagError == true) {
				return;
			}

			PreparedStatement preparedStatement1 = null;
			preparedStatement1 = connection.prepareStatement(
					"INSERT INTO pizza(id_pizza, size, id_ingr, number_ingr) VALUES (?, ?, (SELECT id FROM ingredient_dict where name = ?), ?)");
			PreparedStatement preparedStatement2 = null;
			preparedStatement2 = connection.prepareStatement(
					"UPDATE storage SET number_ingr=number_ingr-? WHERE id_ingr = (SELECT id FROM ingredient_dict "
							+ " where name = ?)");

			ResultSet result2 = statement.executeQuery("SELECT MAX(id_pizza) FROM pizza");

			int max = 0;
			while (result2.next()) {
				max = result2.getInt("MAX");
			}

			if (max == 0) {
				max = 1;
			} else {
				max = max + 1;
			}
			System.out.println("Забираем игридиенты для приготовления пиццы");
			connection.setAutoCommit(false);
			try {

				for (int p = 0; p < newTask.getListIngredients().size(); p++) {

					String ingrName = newTask.getListIngredients().get(p).getName();
					int ingrNumber = newTask.getListIngredients().get(p).getNumber();

					preparedStatement1.setInt(1, max);
					preparedStatement1.setInt(2, newTask.getSize());
					preparedStatement1.setString(3, ingrName);
					preparedStatement1.setInt(4, ingrNumber);
					preparedStatement1.executeUpdate();

					preparedStatement2.setInt(1, ingrNumber);
					preparedStatement2.setString(2, ingrName);
					preparedStatement2.executeUpdate();

				}
				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				System.err.println("Ошибка при вычитании ингридиентов ");
			}
			connection.setAutoCommit(true);
			System.out.println("Пицца готова");
		} catch (Exception ex) {
			System.err.println("Ошибка: " + ex);
		}
	}
}
