package org.example.jdbc.course.hw.hw02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		ArrayList<Abonent> abonents = new ArrayList<>();

		String url = "jdbc:mysql://localhost:3306/study_tech";
		String username = "root";
		String password = "root";
		Class.forName("com.mysql.cj.jdbc.Driver");

		try (Connection connection = DriverManager.getConnection(url, username, password);
		     BufferedReader sqlFile = new BufferedReader(new FileReader("C:\\java\\projects\\studyTech\\src\\main\\java\\org\\example\\jdbc\\course\\hw\\hw02\\hw21.sql"));
		     Scanner scanner = new Scanner(sqlFile);
		     Statement statement = connection.createStatement()) {

			String line;
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				if (line.endsWith(";")) {
					line = line.substring(0, line.length() - 1);
				}
				statement.executeUpdate(line);
			}

			ResultSet rs = null;
			try {
				rs = statement.executeQuery("SELECT * FROM Phonebook");
				while (rs.next()) {
					int id = rs.getInt(1);
					String lastName = rs.getString(2);
					int number = rs.getInt(3);
//					System.out.println("ID: " + id + ", LastName: " + lastName + ", Number: " + number);
					abonents.add(new Abonent(id, lastName, number));
				}
				abonents.add(new Abonent(abonents.size()+1, "Moddy", 7448521));

				// Добавьте в коллекцию abonents новый объект типа класса Abonent и
				// передайте значения всех полей одной записи объекта ResultSet в его конструктор.
				// Выведите в консоль все элементы коллекции, удовлетворяющие условие id > 3.

				for (Abonent abonent : abonents) {
					if (abonent.id > 3)
						System.out.println("ID: " + abonent.id + ", LastName: " + abonent.lastName + ", Number: " + abonent.number);
				}

//				rs = statement.executeQuery("SELECT * FROM Phonebook WHERE id > 3");
//				while (rs.next()) {
//					int id = rs.getInt(1);
//					String lastName = rs.getString(2);
//					int number = rs.getInt(3);
//					System.out.println("ID: " + id + ", LastName: " + lastName + ", Number: " + number);
//				}

			} catch (SQLException ex) {
				System.err.println("SQLException message: " + ex.getMessage());
				System.err.println("SQLException SQL state: " + ex.getSQLState());
				System.err.println("SQLException error code: " + ex.getErrorCode());
			} finally {
				if (rs != null) {
					rs.close();
				} else {
					System.err.println("Ошибка чтения данных с БД!");
				}
			}


		}
	}
}
