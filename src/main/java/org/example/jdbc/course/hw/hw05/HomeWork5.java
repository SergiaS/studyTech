package org.example.jdbc.course.hw.hw05;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.List;

import static org.example.jdbc.Jdbc.getNewConnection;

public class HomeWork5 {
	public static void main(String[] args) throws SQLException {

		String command1 = "INSERT INTO Phonebook (last_name, phone) VALUES ('test1', 7785412)";
		String command2 = "INSERT INTO Phonebook (last_name, phone) VALUES ('test2', 7882153)";
		String command3 = "INSERT INTO Phonebook (last_name, phone) VALUES ('test3', 2445874)";
		List<String> newAbonents = List.of(command1, command2, command3);

		groupUpdate(getNewConnection(), newAbonents);
	}

	public static void groupUpdate(Connection conn, List<String> abonents) throws SQLException {
		try {
			Statement stat = conn.createStatement();
			conn.setAutoCommit(false);
			for (String abonent : abonents) {
				stat.addBatch(abonent);
			}
			stat.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		}
	}
}
