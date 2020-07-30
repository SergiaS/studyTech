package org.example.jdbc.course.lesson5;

import java.sql.*;

import static org.example.jdbc.Jdbc.getNewConnection;

public class MainLesson5 {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// Создаем подключение и создаем объект типа Statement
		try(Connection connection = getNewConnection();
		    Statement stat = connection.createStatement()) {

			// Записываем команды создания новой таблицы и ее наполнения в строковые переменные
			String createTable = "CREATE TABLE IF NOT EXISTS Fruit (name VARCHAR(15) NOT NULL, amount INTEGER, price DOUBLE NOT NULL, PRIMARY KEY (name))";
			String command1 = "INSERT INTO Fruit (name, amount, price) VALUES ('Apple', 200, 3.50)";
			String command2 = "INSERT INTO Fruit (name, amount, price) VALUES ('Orange', 50, 5.50)";
			String command3 = "INSERT INTO Fruit (name, amount, price) VALUES ('Lemon', 50, 4.50)";
			String command4 = "INSERT INTO Fruit (name, amount, price) VALUES ('Pineapple', 20, 7.00)";

			// Отключаем режим автоматической фиксации результатов выполнения команд SQL
			connection.setAutoCommit(false);

			// Выполняем команды запросов
			stat.executeUpdate(createTable);
			stat.executeUpdate(command1);

			// Устанавливаем точку сохранения
			Savepoint spt = connection.setSavepoint();
			stat.executeUpdate(command2);
			stat.executeUpdate(command3);
			stat.executeUpdate(command4);
			// Сохраняем результаты выполнения транзакции
//			connection.commit();

			// Отменяем транзакции
			connection.rollback(spt);
			connection.commit();
			connection.releaseSavepoint(spt);

			// Устанавливаем режим автоматической фиксации
//            connection.setAutoCommit(true);
			// Выполняем команды группой
//            stat.executeUpdate(createTable);
//            stat.addBatch(command1);
//            stat.addBatch(command2);
//            stat.addBatch(command3);
//            stat.addBatch(command4);
			// Собираем команды в группу
//            stat.executeBatch();
		}
	}
}
