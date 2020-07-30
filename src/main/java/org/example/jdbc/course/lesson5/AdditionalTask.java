package org.example.jdbc.course.lesson5;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import static org.example.jdbc.Jdbc.getNewConnection;

public class AdditionalTask {
	public static void main(String[] args) throws SQLException {
		try(Connection connection = getNewConnection();
		    Statement stat = connection.createStatement()) {

			String dropTable = "DROP TABLE IF EXISTS Vegetables";
			String createTable = "CREATE TABLE IF NOT EXISTS Vegetables (name VARCHAR(15) NOT NULL, amount INTEGER, price DOUBLE NOT NULL, PRIMARY KEY (name))";
			String command1 = "INSERT INTO Vegetables (name, amount, price) VALUES ('Tomato', 20, 5.99)";
			String command2 = "INSERT INTO Vegetables (name, amount, price) VALUES ('Potatoes', 75, 3.99)";
			String command3 = "INSERT INTO Vegetables (name, amount, price) VALUES ('Beet', 20, 8.58)";

			connection.setAutoCommit(false);
			Savepoint spt = connection.setSavepoint();
			stat.executeUpdate(dropTable);
			stat.executeUpdate(createTable);
			stat.executeUpdate(command1);
			stat.executeUpdate(command2);
			stat.executeUpdate(command3);
			connection.rollback();
			connection.commit();
			connection.releaseSavepoint(spt);

//			stat.executeUpdate(createTable);
//			stat.addBatch(command1);
//			stat.addBatch(command2);
//			stat.addBatch(command3);
//			stat.executeBatch();
		}
	}
}
