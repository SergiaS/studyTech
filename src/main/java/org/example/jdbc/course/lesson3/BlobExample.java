package org.example.jdbc.course.lesson3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

import static org.example.jdbc.Jdbc.getNewConnection;

public class BlobExample {
	public static void main(String[] args) throws SQLException, IOException {

		// record image to db
		try(Connection connection = getNewConnection();
		    Statement statement = connection.createStatement()) {
			statement.executeUpdate("CREATE TABLE Images (name VARCHAR(15), d DATE, image BLOB)");

			PreparedStatement preparedStatement = null;
			try {
				BufferedImage image = ImageIO.read(new File("smile.jpg"));
				Blob smile = connection.createBlob();
				try(OutputStream outputStream = smile.setBinaryStream(1)) {
					ImageIO.write(image, "jpg", outputStream);
				}
				preparedStatement = connection.prepareStatement("INSERT INTO Images (name, d, image) VALUES (?, {d ?}, ?)");
				preparedStatement.setString(1, "Smile");
				preparedStatement.setDate(2, Date.valueOf("2020-07-28"));
				preparedStatement.setBlob(3, smile);
				preparedStatement.execute();

				// read image form db
				ResultSet rs = null;
				try {
					rs = preparedStatement.executeQuery("SELECT * FROM Images");
					while (rs.next()) {
						Blob newSmile = rs.getBlob("image");
						BufferedImage image1 = ImageIO.read(newSmile.getBinaryStream());
						File outputFile = new File("saved.jpg");
						ImageIO.write(image1, "jpg", outputFile);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					if (rs != null) {
						rs.close();
					} else {
						System.err.println("ERROR");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				preparedStatement.close();
			}
		}
	}
}
