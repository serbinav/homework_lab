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
	private static final int DB_PORT = 5432;
	private static final String DB_NAME = "test";
	private static final String DB_USER = "postgres";
	private static final String DB_PASSWORD = "12345678";

	private static final String SELECT_COUNT_STORAGE = "SELECT count(*) FROM storage";
	private static final String INSERT_STORAGE = "INSERT INTO storage (id, id_ingr, number_ingr) VALUES (?, ?, ?)";
	private static final String SELECT_INGREDIENT_STORAGE = "SELECT name, number_ingr FROM storage,ingredient_dict WHERE storage.id_ingr = ingredient_dict.id and name = ?";
	private static final String SELECT_MAX_PIZZA = "SELECT MAX(id_pizza) FROM pizza";
	private static final String INSERT_PIZZA = "INSERT INTO pizza(id_pizza, size, id_ingr, number_ingr) VALUES (?, ?, (SELECT id FROM ingredient_dict WHERE name = ?), ?)";
	private static final String UPDATE_STORAGE = "UPDATE storage SET number_ingr=number_ingr-? WHERE id_ingr = (SELECT id FROM ingredient_dict WHERE name = ?)";

	/**
	 * проверка "есть ли ингридиенты на складе"
	 **/
	public int selectStorage(Connection conn) {
		int count = 0;
		try (Statement selectStatement = conn.createStatement();
				ResultSet selectResult = selectStatement.executeQuery(SELECT_COUNT_STORAGE)) {
			if (selectResult.next()) {
				count = selectResult.getInt("count");
			}
		} catch (SQLException es) {
			System.err.println("Ошибка в проверке наличия ингридиентов на складе: " + es);
		}
		return count;
	}

	/**
	 * транзакция "добавление ингридиентов на склад"
	 **/
	public void insertStorage(Connection conn) {
		try (PreparedStatement prepStateInsertStorage = conn.prepareStatement(INSERT_STORAGE)) {
			for (int i = 1; i < 4; i++) {
				prepStateInsertStorage.setInt(1, i);
				prepStateInsertStorage.setInt(2, i);
				prepStateInsertStorage.setInt(3, 100);
				prepStateInsertStorage.executeUpdate();
			}
			conn.commit();
		} catch (SQLException e) {
			System.err.println("Ошибка при добавлении ингридиентов на склад(: " + e);
			try {
				conn.rollback();
			} catch (SQLException er) {
				System.err.println("Ошибка при добавлении ингридиентов на склад(откат транзакции): " + er);
			}
		}
	}

	/**
	 * получении количества ингридиента со склада
	 **/
	public int selectIngredient(Connection conn, String ingrName) {
		int ingrNumberStorage = 0;
		try (PreparedStatement prepStateSelect = conn.prepareStatement(SELECT_INGREDIENT_STORAGE)) {
			prepStateSelect.setString(1, ingrName);
			try (ResultSet resultSelect = prepStateSelect.executeQuery()) {
				if (resultSelect.next()) {
					ingrNumberStorage = resultSelect.getInt("number_ingr");
				}
			} catch (SQLException esr) {
				System.err.println(
						"Ошибка в получении количества ингридиентов со склада(в получении результатов): " + esr);
			}
		} catch (SQLException ep) {
			System.err.println("Ошибка в получении количества ингридиентов со склада: " + ep);
		}
		return ingrNumberStorage;
	}

	/**
	 * проверка "есть ли готовые пиццы в списке"
	 **/
	public int selectPizza(Connection conn) {
		int max = 0;
		try (Statement selectMaxStatement = conn.createStatement();
				ResultSet selectMaxResult = selectMaxStatement.executeQuery(SELECT_MAX_PIZZA)) {

			if (selectMaxResult.next()) {
				max = selectMaxResult.getInt("MAX");
			}
		} catch (SQLException es) {
			System.err.println("Ошибка при проверке списка готовых пицц: " + es);
		}
		return max;
	}

	/**
	 * транзакция
	 * "добавление пиццы в список готовых и вычитании ингридиентов со склада"
	 **/
	public void insertPizzaUpdateStorage(Connection conn, Task newTask, int nextNumber) {
		System.out.println("Забираем игридиенты для приготовления пиццы");
		try (PreparedStatement prepStateInsertPizza = conn.prepareStatement(INSERT_PIZZA);
				PreparedStatement prepStateUpdateStorage = conn.prepareStatement(UPDATE_STORAGE)) {

			for (int p = 0; p < newTask.getListIngredients().size(); p++) {
				String ingrName = newTask.getListIngredients().get(p).getName();
				int ingrNumber = newTask.getListIngredients().get(p).getNumber();

				prepStateInsertPizza.setInt(1, nextNumber);
				prepStateInsertPizza.setInt(2, newTask.getSize());
				prepStateInsertPizza.setString(3, ingrName);
				prepStateInsertPizza.setInt(4, ingrNumber);
				prepStateInsertPizza.executeUpdate();

				prepStateUpdateStorage.setInt(1, ingrNumber);
				prepStateUpdateStorage.setString(2, ingrName);
				prepStateUpdateStorage.executeUpdate();
			}
			conn.commit();
		} catch (SQLException e) {
			System.err
					.println("Ошибка при добавлении пиццы в список готовых или вычитании ингридиентов со склада: " + e);
			try {
				conn.rollback();
			} catch (SQLException er) {
				System.err.println(
						"Ошибка при добавлении пиццы в список готовых или вычитании ингридиентов со склада(откат транзакции): "
								+ er);
			}
		}
	}

	/**
	 * проверка "достаточно ли ингридиентов на склада для обработки заказа"
	 **/
	public boolean checkIngrNumber(Connection conn, Task newTask) {
		System.out.println("Проверям наличие ингридиентов на складе");
		for (int q = 0; q < newTask.getListIngredients().size(); q++) {
			String ingrName = newTask.getListIngredients().get(q).getName();
			int ingrNumber = newTask.getListIngredients().get(q).getNumber();

			if (selectIngredient(conn, ingrName) < ingrNumber) {
				System.err.println("Ошибка: недостаточно ингридиента: \"" + ingrName + "\" для обработки заказа");
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		TestJdbc app = new TestJdbc();

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

			if (app.selectStorage(connection) == 0) {
				System.err.println("Ошибка: склад пуст, ингридиенты будут добавлены");

				connection.setAutoCommit(false);
				app.insertStorage(connection);
				connection.setAutoCommit(true);

			} else {
				System.out.println("На складе присутствуют запасы");
			}

			Person client = new Person(1);
			Task newTask = client.createTask();
			System.out.println("Сгенерировали заказ");

			if (app.checkIngrNumber(connection, newTask) == true) {
				return;
			}

			int nextNumber = app.selectPizza(connection);

			if (nextNumber == 0) {
				nextNumber = 1;
			} else {
				nextNumber = nextNumber + 1;
			}

			connection.setAutoCommit(false);
			app.insertPizzaUpdateStorage(connection, newTask, nextNumber);
			connection.setAutoCommit(true);

			System.out.println("Пицца готова");
		} catch (SQLException ex) {
			System.err.println("Ошибка: " + ex);
		}
	}
}
