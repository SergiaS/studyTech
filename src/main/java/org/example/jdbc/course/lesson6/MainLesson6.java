package org.example.jdbc.course.lesson6;

import java.sql.*;

import static org.example.jdbc.Jdbc.getNewConnection;

public class MainLesson6 {
	public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException {
		try(Connection conn = getNewConnection();
		    Statement stat = conn.createStatement()) {
			// Отключаем режим автоматической фиксации результатов выполнения команд SQL
			conn.setAutoCommit(false);

			// dirty read
//            conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED); // чтение разрешено
//            stat.executeUpdate("UPDATE Books SET price = 100 WHERE bookId = 1");
//            new OtherTransaction().start();
//            Thread.sleep(2000);
//            conn.rollback();

			// non-repeatable read
//            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); // чтение разрешено
			conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ); // чтение заблокировано (это значение достаточно установить в одной транзакции)
//            ResultSet resultSet = stat.executeQuery("SELECT * FROM Books");
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString("name") + " " + resultSet.getDouble(3));
//            }
//            new OtherTransaction().start();
//            Thread.sleep(2000);
//
//            ResultSet resultSet2 = stat.executeQuery("SELECT * FROM Books");
//            while (resultSet2.next()) {
//                System.out.println(resultSet2.getString("name") + " " + resultSet2.getDouble(3));
//            }

			// phantom read
//            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); // чтение разрешено
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE); // чтение заблокировано
			ResultSet resultSet = stat.executeQuery("SELECT * FROM Books WHERE bookId > 5");
			while (resultSet.next()) {
				System.out.println(resultSet.getString("name") + " " + resultSet.getDouble(3));
			}
			new OtherTransaction().start();
			Thread.sleep(2000);

			ResultSet resultSet2 = stat.executeQuery("SELECT * FROM Books WHERE bookId > 5");
			while (resultSet2.next()) {
				System.out.println(resultSet2.getString("name") + " " + resultSet2.getDouble(3));
			}
		}
	}

	// Класс, в котором реализуются транзакции для чтения
	static class OtherTransaction extends Thread {
		@Override
		public void run() {
			try (Connection conn = getNewConnection();
			     Statement stat = conn.createStatement()) {
				// Отключаем режим автоматической фиксации результатов выполнения команд SQL
				conn.setAutoCommit(false);

				// dirty read
//                conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED); // чтение заблокировано
//                ResultSet rs = stat.executeQuery("SELECT * FROM Books");
//                while (rs.next()) {
//                    System.out.println(rs.getString("name") + " " + rs.getDouble(3));
//                }

				// non-repeatable read
//                conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); // чтение разрешено
//                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ); // чтение заблокировано
//                stat.executeUpdate("UPDATE Books SET price = price + 20 WHERE name = 'Solomon key'");
//                conn.commit();

				// phantom read
//                conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); // чтение разрешено
				conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE); // чтение заблокировано
				stat.executeUpdate("INSERT INTO Books (name, price) VALUES ('new Book', 10)", Statement.RETURN_GENERATED_KEYS);
				ResultSet keys = stat.getGeneratedKeys();
				int lastKey = 1;
				while (keys.next()) {
					lastKey = keys.getInt(1);
				}
				System.out.println("Last Key: " + lastKey);
				conn.commit();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
