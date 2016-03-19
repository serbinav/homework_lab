package org.jdbc_release;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.ds.PGPoolingDataSource;

public class CommentJdbc {
	
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
		
//		SELECT name, number_ingr FROM storage,ingredient_dict 
//		where storage.id_ingr = ingredient_dict.id;
		
//		INSERT INTO pizza(size, id_ingr, number_ingr) VALUES (7, 3, 50);
		
//		INSERT INTO storage(id_ingr, number_ingr) VALUES (3, 100);


		
        /*Connection connection = null;
        //URL к базе состоит из протокола:подпротокола://[хоста]:[порта_СУБД]/[БД] и других_сведений
        String url = DB_SERVER_NAME+"/"+DB_NAME;
        //Имя пользователя БД
        String name = DB_USER;
        //Пароль
        String password = DB_PASSWORD;*/
        //try {
            //Загружаем драйвер
            //Class.forName("org.postgresql.Driver");
            //System.out.println("Драйвер подключен");
            //Создаём соединение
		
        try (Connection connection = ds.getConnection()){	
            System.out.println("Соединение установлено");
            
            //Для использования SQL запросов существуют 3 типа объектов:
            //1.Statement: используется для простых случаев без параметров
            Statement statement = null;

            statement = connection.createStatement();
            //Выполним запрос
            


            ResultSet result1 = statement.executeQuery(
                    "SELECT name, number_ingr FROM storage,ingredient_dict "
                    + "where storage.id_ingr = ingredient_dict.id;");
            //result это указатель на первую строку с выборки
            //чтобы вывести данные мы будем использовать 
            //метод next() , с помощью которого переходим к следующему элементу
            System.out.println("Выводим statement");
            while (result1.next()) {
                System.out.println("Номер в выборке #" + result1.getRow()
                        + "\t Количество в базе #" + result1.getInt("number_ingr")
                        + "\t" + result1.getString("name"));
            }
            // Вставить запись
            /*statement.executeUpdate(
                    "INSERT INTO ingredient(name) values('name')");
            //Обновить запись
            statement.executeUpdate(
                    "UPDATE ingredient SET name = 'admin' where id = 1");
            */
            
            //2.пример транзакции 
        	Statement st = connection.createStatement(); 
    		connection.setAutoCommit(false); 
    		try { 
    			st.execute( 
    		    		"INSERT INTO pizza(id_pizza, size, id_ingr, number_ingr) VALUES (1, 7, 3, 50)"
    					);
    			st.executeUpdate( 
    					"UPDATE storage SET number_ingr=number_ingr-50 WHERE id_ingr = 3"); 
    			
    			st.execute( 
    		    		"INSERT INTO pizza(id_pizza, size, id_ingr, number_ingr) VALUES (1, 7, 1, 25)"
    					);
    			st.executeUpdate( 
    					"UPDATE storage SET number_ingr=number_ingr-25 WHERE id_ingr = 1"); 

    			connection.commit(); // фиксируем транзакцию 
    		} catch (SQLException e) { 
    		  // Если что-то пошло не так, откатываем всю транзакцию 
    			connection.rollback(); 
    		} 
            
            //3.PreparedStatement: предварительно компилирует запросы, 
            //которые могут содержать входные параметры
            PreparedStatement preparedStatement = null;
            // ? - место вставки нашего значеня
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM ingredient where id > ? and id < ?");
            //Устанавливаем в нужную позицию значения определённого типа
            preparedStatement.setInt(1, 2);
            preparedStatement.setInt(2, 10);
            //выполняем запрос
            ResultSet result2 = preparedStatement.executeQuery();
            
            System.out.println("Выводим PreparedStatement");
            while (result2.next()) {
                System.out.println("Номер в выборке #" + result2.getRow()
                        + "\t Номер в базе #" + result2.getInt("id")
                        + "\t" + result2.getString("name"));
            }
            
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO ingredient(name) values(?)");
            preparedStatement.setString(1, "user_name");
            //метод принимает значение без параметров
            //темже способом можно сделать и UPDATE
            preparedStatement.executeUpdate();
              
        } catch (Exception ex) {
            //выводим наиболее значимые сообщения
            System.err.println(ex);
            //Logger.getLogger(JDBCtest.class.getName()).log(Level.SEVERE, null, ex);
        } /*finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.err.println(ex);
                    //Logger.getLogger(JDBCtest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }*/

    }

		/*Storage newStorage = new StorageInList();
		Pizza newPizza = new Pizza();
		List<Ingredient> listComponent = new ArrayList<>();

		listComponent.add(new Ingredient("сыр", 100));
		listComponent.add(new Ingredient("колбаса", 100));
		listComponent.add(new Ingredient("перец", 100));
		newStorage.setListComponent(listComponent);

		ProductionPizza once = new StorageInCollection();
		int sizePizza = once.selectPizzaSize();
		while (sizePizza <= 0 || sizePizza > 4100) {
			sizePizza = once.selectPizzaSize();
		}
		newPizza.setSizePizza(sizePizza);

		newPizza.setListIngredients(once.chooseIngredientForPizza(newStorage));

		once.printOrderPizza(newPizza);*/

}
