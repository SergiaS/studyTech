package org.example.jdbc.course.lesson4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.jdbc.Jdbc.getNewConnection;

public class MainLesson4 {
	public static void main(String[] args) throws SQLException {
		try (Connection connection = getNewConnection();
		     Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
			ResultSet rs = null;
			try {
				rs = statement.executeQuery("SELECT * FROM books");
				while (rs.next()) {
					int id = rs.getInt(1);
					double price = rs.getDouble(3);
					if (id == 2) {
						rs.updateString("name", "Spartacus");
						rs.updateDouble(3, price - 10);
						rs.updateRow();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (rs != null) {
					rs.close();
				} else {
					System.out.println("ERROR!");
				}
			}
		}
	}
}
