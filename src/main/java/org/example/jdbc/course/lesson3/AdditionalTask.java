package org.example.jdbc.course.lesson3;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.jdbc.Jdbc.getNewConnection;

public class AdditionalTask {
	public static void main(String[] args) throws SQLException {
		try (Connection connection = getNewConnection()) {
			CallableStatement cs = null;
			try {
				cs = connection.prepareCall("{CALL view()}");
				boolean hasRes = cs.execute();
				ResultSet rs = cs.getResultSet();
				try {
					while (hasRes) {
						rs = cs.getResultSet();
						while (rs.next()) {
							System.out.println("Id: " + rs.getInt(1) + ", Name: " + rs.getString(2) + ", Price: " + rs.getDouble(3));
						}
						hasRes = cs.getMoreResults();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					if (rs != null) {
						rs.close();
					} else {
						System.err.println("ERROR!!!");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				cs.close();
			}
		}
	}
}
