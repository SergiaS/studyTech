package org.example.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Jdbc {
	public static void main(String[] args) {

	}

	public static Connection getNewConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/study_tech";
		String username = "root";
		String password = "root";

		return DriverManager.getConnection(url, username, password);
	}
}
