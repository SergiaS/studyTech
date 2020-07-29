package org.example.jdbc.course.lesson4;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

import static org.example.jdbc.Jdbc.getNewConnection;

public class RowSetLesson {
	static String url = "jdbc:mysql://localhost:3306/study_tech";
	static String userName = "root";
	static String password = "root";

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
//		ResultSet resultSet = getResSet();
////		while (resultSet.next()) {
////			System.out.println(resultSet.getString("name"));
////		}
//		CachedRowSet rowSet = (CachedRowSet) resultSet;
//		rowSet.setCommand("SELECT * FROM books WHERE price > ?");
//		rowSet.setDouble(1, 30);
//		rowSet.setUrl(url);
//		rowSet.setUsername(userName);
//		rowSet.setPassword(password);
//		rowSet.execute();
//
//		// перемещаемся на 2ую позицию набора строк, НЕ БД!!!
//		rowSet.absolute(2);
//		rowSet.deleteRow();
//		rowSet.beforeFirst();
//		Connection connection = getNewConnection();
//		connection.setAutoCommit(false);
//		rowSet.acceptChanges(connection);
//
//		while (rowSet.next()) {
//			String name = rowSet.getString("name");
//			double price = rowSet.getDouble(3);
//			System.out.println(name + ": " + price);
//		}

		insertNew("Terminator", 67.99);
	}

	static ResultSet getResSet() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try(Connection connection = DriverManager.getConnection(url, userName, password);
		    Statement statement = connection.createStatement()) {

			ResultSet rs = statement.executeQuery("SELECT * FROM books");
			RowSetFactory factory = RowSetProvider.newFactory();
			CachedRowSet crs = factory.createCachedRowSet();
			crs.populate(rs);
			return crs;
		}
	}

	static void insertNew(String name, double price) throws SQLException {
		try(Connection connection = getNewConnection();
		    Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);) {

			ResultSet rs = null;
			try {
				String query = "SELECT * FROM books";
				rs = statement.executeQuery(query);

				rs.moveToInsertRow();
//				rs.last();
				rs.updateString("name", name);
				rs.updateDouble("price", price);
				rs.insertRow();

//				String query = "INSERT INTO books(name, price) VALUES ("+ name +", " + price + ")";

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
