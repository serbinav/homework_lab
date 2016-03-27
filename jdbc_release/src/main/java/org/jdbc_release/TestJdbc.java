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

	public int selectStorage(Connection conn) {
		Statement selectStatement = null;
		ResultSet selectResult = null;
		int count = 0;
		try {
			selectStatement = conn.createStatement();
			selectResult = selectStatement.executeQuery("select count(*) from storage");

			while (selectResult.next()) {
				count = selectResult.getInt("count");
			}
		} catch (SQLException es) {
			System.err.println("Ошибка в проверке наличия ингридиентов на складе: " + es);
		} finally {
			try {
				if (selectResult != null)
					selectResult.close();
			} catch (SQLException esr) {
				System.err.println(
						"Ошибка в проверке наличия ингридиентов на складе(в получении результатов): " + esr);
			} finally {
				try {
					if (selectStatement != null)
						selectStatement.close();
				} catch (SQLException ess) {
					System.err.println(
							"Ошибка в проверке наличия ингридиентов на складе(в выполнении запроса): " + ess);
				}
			}
		}
		return count;
	}

	public void insertStorage(Connection conn) {
		PreparedStatement prepStateInsertStorage = null;
		String insertString = "INSERT INTO storage (id, id_ingr, number_ingr) VALUES (?, ?, ?)";

		try {
			conn.setAutoCommit(false);
			prepStateInsertStorage = conn.prepareStatement(insertString);

			for (int i = 1; i < 4; i++) {
				prepStateInsertStorage.setInt(1, i);
				prepStateInsertStorage.setInt(2, i);
				prepStateInsertStorage.setInt(3, 100);
				prepStateInsertStorage.executeUpdate();
			}
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException er) {
				System.err.println("Ошибка при добавлении ингридиентов на склад(откат транзакции) " + er);
			}
		} finally {
			if (prepStateInsertStorage != null) {
				try {
					prepStateInsertStorage.close();
				} catch (SQLException ec) {
					System.err.println("Ошибка при добавлении ингридиентов на склад(закрытии соединения) " + ec);
				}
			}
			try {
				conn.setAutoCommit(true);
			} catch (SQLException eac) {
				System.err.println("Ошибка при добавлении ингридиентов на склад(включении автокоммита) " + eac);
			}
		}
	}

	public int selectIngredient(Connection conn, String ingrName) {
		PreparedStatement prepStateSelect = null;
		String selectString = "SELECT name, number_ingr FROM storage,ingredient_dict"
				+ " where storage.id_ingr = ingredient_dict.id and name = ?";
		int ingrNumberStorage = 0;
		ResultSet resultSelect = null;
		try {
			prepStateSelect = conn.prepareStatement(selectString);

			prepStateSelect.setString(1, ingrName);
			resultSelect = prepStateSelect.executeQuery();

			while (resultSelect.next()) {
				ingrNumberStorage = resultSelect.getInt("number_ingr");
			}
		} catch (SQLException ep) {
			System.err.println("Ошибка в получении количества ингридиентов со склада: " + ep);
		} finally {
			try {
				if (resultSelect != null)
					resultSelect.close();
			} catch (SQLException esr) {
				System.err.println(
						"Ошибка в получении количества ингридиентов со склада(в получении результатов): "
								+ esr);
			} finally {
				try {
					if (prepStateSelect != null)
						prepStateSelect.close();
				} catch (SQLException pss) {
					System.err.println(
							"Ошибка в получении количества ингридиентов со склада(в выполнении запроса): "
									+ pss);
				}
			}
		}
		return ingrNumberStorage;
	}
	
	public int selectPizza(Connection conn) {
		Statement selectMaxStatement = null;
		ResultSet selectMaxResult = null;
		int max = 0;
		try {
			selectMaxStatement = conn.createStatement();
			selectMaxResult = selectMaxStatement.executeQuery("SELECT MAX(id_pizza) FROM pizza");

			while (selectMaxResult.next()) {
				max = selectMaxResult.getInt("MAX");
			}
		} catch (SQLException es) {
			System.err.println("Ошибка при проверке списка готовых пицц: " + es);
		} finally {
			try {
				if (selectMaxResult != null)
					selectMaxResult.close();
			} catch (SQLException esr) {
				System.err.println("Ошибка при проверке списка готовых пицц(в получении результатов): " + esr);
			} finally {
				try {
					if (selectMaxStatement != null)
						selectMaxStatement.close();
				} catch (SQLException ess) {
					System.err.println("Ошибка при проверке списка готовых пицц(в выполнении запроса): " + ess);
				}
			}
		}
		return max;
	}
	
	public void insertPizzaUpdateStorage(Connection conn, Task newTask, int nextNumber) {
		PreparedStatement prepStateInsertPizza = null;
		PreparedStatement prepStateUpdateStorage = null;

		String insertPizzaString = "INSERT INTO pizza(id_pizza, size, id_ingr, number_ingr) VALUES (?, ?, (SELECT id FROM ingredient_dict where name = ?), ?)";
		String updateStorageString = "UPDATE storage SET number_ingr=number_ingr-? WHERE id_ingr = (SELECT id FROM ingredient_dict "
				+ " where name = ?)";

		System.out.println("Забираем игридиенты для приготовления пиццы");
		try {
			conn.setAutoCommit(false);
			prepStateInsertPizza = conn.prepareStatement(insertPizzaString);
			prepStateUpdateStorage = conn.prepareStatement(updateStorageString);

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
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.err.println("Ошибка при вычитании ингридиентов ");
		} finally {
			if (prepStateInsertPizza != null) {
				try {
					prepStateInsertPizza.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (prepStateUpdateStorage != null) {
				try {
					prepStateUpdateStorage.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
	// разбить на модули 20 - 40 строк
	// вложенность не больее 4 уровней
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

				app.insertStorage(connection);

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

				if (app.selectIngredient(connection,ingrName) < ingrNumber) {
					System.err.println("Ошибка: недостаточно ингридиента: \"" + ingrName + "\" для обработки заказа");
					flagError = true;
					break;
				}
			}

			if (flagError == true) {
				return;
			}
			int nextNumber = app.selectPizza(connection);
			
			if (nextNumber == 0) {
				nextNumber = 1;
			} else {
				nextNumber = nextNumber + 1;
			}
			
			app.insertPizzaUpdateStorage(connection, newTask, nextNumber);

			System.out.println("Пицца готова");
		} catch (SQLException ex) {
			System.err.println("Ошибка: " + ex);
		}
	}
}
