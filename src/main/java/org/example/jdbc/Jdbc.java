package org.example.jdbc;

import org.example.model.Person;

import java.io.*;
import java.sql.*;

public class Jdbc {
	public static void main(String[] args) {

		try(Connection connection = getConnection()) {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM persons");

			while (resultSet.next()) {
				System.out.println(resultSet.getString(3));
			}
			resultSet.close();
			statement.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static int executeUpdate(String query) throws SQLException {
		Statement statement = getConnection().createStatement();
		return statement.executeUpdate(query);
	}

	private static void createPersonTableAndInitData() throws IOException, SQLException {

		StringBuilder sb = new StringBuilder();
		String line;
		BufferedReader bufferedReader = new BufferedReader(
				new FileReader("src/main/resources/db/initMySQL.sql"));
		while ((line = bufferedReader.readLine()) != null) {
			sb.append(line);
		}
		executeUpdate(String.valueOf(sb));
	}

	public static Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/study_tech";
		String username = "root";
		String password = "root";

		return DriverManager.getConnection(url, username, password);
	}
}
