package org.example.jdbc.course.lesson3;

import java.sql.*;

public class MainLesson3 {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		String url = "jdbc:mysql://localhost:3306/study_tech";
		String userName = "root";
		String password = "root";
		Class.forName("com.mysql.cj.jdbc.Driver");

		// Создаем подключение, объект Statement и читаем командный файл
		// Scanner используем в связке с BufferedReader для расширения функционала взаимодействия с читаемым файлом
		try (Connection connection = DriverManager.getConnection(url, userName, password);) {
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = connection.prepareStatement("INSERT INTO books (name, price) VALUES (?, ?)");
				preparedStatement.setString(1, "Shindler's list");
				preparedStatement.setDouble(2, 32.5);
				preparedStatement.execute();

				ResultSet rs = null;
				try {
					rs = preparedStatement.executeQuery("SELECT * FROM books");
					while (rs.next()) {
						int id = rs.getInt(1);
						String name = rs.getString(2);
						double price = rs.getDouble(3);
						System.out.println("id: " + id + ", name: " + name + ", price: " + price);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					if (rs != null) {
						rs.close();
					} else {
						System.err.println("ERROR");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				preparedStatement.close();
			}

			CallableStatement callStat = null;
			try {
				callStat = connection.prepareCall("{CALL booksCount(?)}");
				callStat.registerOutParameter(1, Types.INTEGER);
				callStat.execute();
				System.out.println("List size is: " + callStat.getInt(1));
			} catch (SQLException e){
				e.printStackTrace();
			} finally {
				callStat.close();
			}
		}
	}
}
