package org.example.jdbc.course;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
Создание первого простого подключения к БД
 */
public class TestConnection {
	public static void main(String[] args) throws ClassNotFoundException {
		String url = "jdbc:mysql://localhost:3306/jdbc_lessons";
		String userName = "root";
		String password = "root";

		// регистрируем наш драйвер
		Class.forName("com.mysql.cj.jdbc.Driver");

		// создаем подключение
		try(Connection connection = DriverManager.getConnection(url, userName, password)){
			System.out.println("Connection successful!");
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
}
