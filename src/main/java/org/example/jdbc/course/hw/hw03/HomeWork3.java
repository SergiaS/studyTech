package org.example.jdbc.course.hw.hw03;

import java.sql.*;
import java.util.Scanner;

import static org.example.jdbc.Jdbc.getNewConnection;

public class HomeWork3 {
	public static void main(String[] args) throws SQLException {
		Scanner sc = new Scanner(System.in);
		int id = sc.nextInt();
		String name = sc.next();

		try(Connection connection = getNewConnection()) {
			PreparedStatement ps = null;
			try {
				ps = connection.prepareStatement("UPDATE books SET name = ? WHERE bookId = ?");
				ps.setString(1, name);
				ps.setInt(2, id);
				ps.execute();

				CallableStatement cs = null;
				try {
					cs = connection.prepareCall("{CALL bookView(?)}");
					cs.registerOutParameter(1, Types.INTEGER);
					cs.execute();
				} catch (SQLException e) {
					if (cs != null) {
						cs.close();
					}
					e.printStackTrace();
				}

				ResultSet rs = null;
				try {
					String query = "SELECT * FROM books WHERE bookId = " + id;
//					String query = "SELECT * FROM books";
					rs = ps.executeQuery(query);
					while (rs.next()) {
						int idBook = rs.getInt(1);
						String nameBook = rs.getString(2);
						double price = rs.getDouble(3);
						System.out.println("id: " + idBook + ", name: " + nameBook + ", price: " + price);
					}
				} catch (SQLException e) {
					if (rs != null) {
						rs.close();
					}
					e.printStackTrace();
				}
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				ps.close();
			}
		}
	}
}
