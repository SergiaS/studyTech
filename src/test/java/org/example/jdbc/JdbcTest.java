package org.example.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JdbcTest {

	private static Connection connection;

	@Before
	public void init() throws SQLException {
		connection = getConnection();
	}

	@After
	public void tearDown() throws Exception {
		connection.close();
	}

	@Test
	public void shouldGetJdbcConnection() throws SQLException {
		try(Connection connection = getConnection()) {
			assertTrue(connection.isValid(1));
			assertFalse(connection.isClosed());
		}
	}

	private Connection getConnection() throws SQLException {
		String url = "jdbc:h2:mem:test";
		String user = "sa";
		String passwd = "sa";
		return DriverManager.getConnection(url, user, passwd);
	}

}
