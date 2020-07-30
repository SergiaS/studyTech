package org.example.jdbc.course.hw.hw04;

import java.sql.*;

import static org.example.jdbc.Jdbc.getNewConnection;

public class HomeWork4 {
	public static void main(String[] args) throws SQLException {

		HomeWork4 hw4 = new HomeWork4();

		try (Connection connection = getNewConnection();
		     Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

			ResultSet rs = statement.executeQuery("SELECT * FROM books");
			hw4.addNewBook(rs, "New SP!", 10.5);

			hw4.showAll(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addNewBook(ResultSet rs, String name, double price) throws SQLException {
		rs.moveToInsertRow();
		rs.updateString("name", name);
		rs.updateDouble("price", price);
		rs.insertRow();
		rs.moveToCurrentRow();
	}

	public void showAll(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		while (rs.next()) {
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String field = rsmd.getColumnName(i);
				String value = rs.getString(field);
				System.out.print(field + ": " + value + " ");
			}
			System.out.println();
		}
	}
}
