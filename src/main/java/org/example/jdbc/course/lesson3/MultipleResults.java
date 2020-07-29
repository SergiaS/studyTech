package org.example.jdbc.course.lesson3;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.jdbc.Jdbc.getNewConnection;

public class MultipleResults {
	public static void main(String[] args) throws SQLException {
		try(Connection connection = getNewConnection()) {
			// cоздаем хранимую процедуру
			CallableStatement callableStatement = null;
			try {
				callableStatement = connection.prepareCall("{CALL tablesCount()}");
				boolean hasResults = callableStatement.execute();
				ResultSet resultSet = null;
				try {
					while (hasResults) {
						resultSet = callableStatement.getResultSet();
						while (resultSet.next()) {
							System.out.println("List size in table: " + resultSet.getInt(1));
						}
						hasResults = callableStatement.getMoreResults();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					if (resultSet != null) {
						resultSet.close();
					} else {
						System.err.println("ERROR");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				callableStatement.close();
			}
		}
	}
}
