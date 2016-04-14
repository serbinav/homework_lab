Для работы данного приложения необходима Java версии 8 (http://www.java.com/ru/download/manual.jsp), 
также необходим maven (http://maven.apache.org/download.cgi) 
и PostgreSQL (http://postgresql.ru.net/download.html)

Для начала "склонируем" проект выполнив команду

	git clone http://studygit.simbirsoft1.com/serbinav86/homework.git
	
Затем нужно развернуть БД из "дампа". Выполним команду

	psql -U postgres
	
После возможно будет запрошен пароль для пользователя postgres, а возможно и нет(все завист от ваших настроек),
вид нашей консоли изменится и станет примерно таким

	postgres=#

Далее введем команду	
	
	\i [путь до директории,где была выполнена команда «git clone http://studygit.simbirsoft1.com/serbinav86/homework.git» + jdbc_release/test_db.backup]
	
Дождемся завершения , затем ввести

	\l
	
В консоли должно появиться подобное сообщение:

                                          Список баз данных
   Имя     | Владелец | Кодировка |     LC_COLLATE      |      LC_CTYPE       
-----------+----------+-----------+---------------------+---------------------
 postgres  | postgres | UTF8      | Russian_Russia.1251 | Russian_Russia.1251 
 template0 | postgres | UTF8      | Russian_Russia.1251 | Russian_Russia.1251 
           |          |           |                     |                     
 template1 | postgres | UTF8      | Russian_Russia.1251 | Russian_Russia.1251 
           |          |           |                     |                     
 test      | postgres | UTF8      | Russian_Russia.1251 | Russian_Russia.1251 
(записей: 4)

Видим то появилась БД test, затем введем команду

	\q
	
Вид нашей консоли станет прежним,
для сборки проекта этого заходим в командную строку и вводим команду 

	cd [путь до директории куда была выполнена команда «git clone http://studygit.simbirsoft1.com/serbinav86/homework.git»]
	
Произойдет смена каталога, затем вводим команду 
	
	mvn install
	
Должна появиться информация, что-то вроде:
	
	[INFO] ------------------------------------------------------------------------
	[INFO] Reactor Summary:
	[INFO]
	[INFO] Maven git application .............................. SUCCESS [  0.434 s]
	[INFO] printing_module .................................... SUCCESS [  3.084 s]
	[INFO] console_release .................................... SUCCESS [  0.526 s]
	[INFO] file_release ....................................... SUCCESS [  0.574 s]
	[INFO] console_app ........................................ SUCCESS [  2.283 s]
	[INFO] thread_release ..................................... SUCCESS [  2.135 s]
	[INFO] jdbc_release ....................................... SUCCESS [  1.614 s]
	[INFO] jpa_release ........................................ SUCCESS [  4.473 s]
	[INFO] ------------------------------------------------------------------------
	[INFO] BUILD SUCCESS
	[INFO] ------------------------------------------------------------------------
	[INFO] Total time: 15.290 s
	[INFO] Finished at: 2016-04-13T19:17:39+04:00
	[INFO] Final Memory: 27M/247M
	[INFO] ------------------------------------------------------------------------

Затем вводим команду: 

	cd [путь до директории,где была выполнена команда «git clone http://studygit.simbirsoft1.com/serbinav86/homework.git» + jdbc_release\target]
	
Произойдет смена каталога, затем вводим команду: 	

	java -jar jdbc_release-0.0.1-jar-with-dependencies.jar
	
В консоли должно появиться подобное сообщение:
	
	Соединение с БД установлено
	Ошибка: склад пуст, ингридиенты будут добавлены
	Сгенерировали заказ
	Проверям наличие ингридиентов на складе
	Забираем игридиенты для приготовления пиццы
	Пицца готова
