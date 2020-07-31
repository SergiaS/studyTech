package org.example.jdbc.course.lesson4;

import java.sql.*;

import static org.example.jdbc.Jdbc.getNewConnection;

public class MainLesson4 {
	public static void main(String[] args) throws SQLException {
		try (Connection connection = getNewConnection();
		     Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
			ResultSet rs = null;
			try {
				// Получаем результирующий набор
				rs = statement.executeQuery("SELECT * FROM books");
				// Обновляем одну из записей в таблице Books
//                while (rs.next()) {
//                    int id = rs.getInt(1);
//                    double price = rs.getDouble(3);
//                    if(id == 4) {
//                        rs.updateString("name" , "Spartacus (discount)");
//                        rs.updateDouble(3, price - 10);
//                        rs.updateRow();
//                    }
//                }

				// Переходим по результирующему набору с помощью различных методов
				if (rs.absolute(2))
					System.out.println(rs.getString("name"));
				if (rs.previous())
					System.out.println(rs.getString("name"));
				if (rs.last())
					System.out.println(rs.getString("name"));

				// Передвигаем курсор на три строки назад
				if (rs.relative(-3)) {
					// Получаем объект метаданных результирующего набора
					ResultSetMetaData rsmd = rs.getMetaData();
					// Выводим строки результирующего набора с помощъю метаданных
					while (rs.next()) {
						for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
							String field = rsmd.getColumnName(i);
							String value = rs.getString(field);
							System.out.print(field + ": " + value + " ");
						}
						System.out.println("");
					}
				}
			} catch (SQLException exc) {
				exc.printStackTrace();
			} finally {
				if (rs != null)
					rs.close();
				else
					System.err.println("Ошибка чтения данных с БД!");
			}
		}
	}
}
