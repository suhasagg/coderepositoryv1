package com.websystique.springmvc.service;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LevelDBUtil {

		public static void close(Connection connection) {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					/*log or print or ignore*/
				}
			}
		}

		public static void close(Statement statement) {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					/*log or print or ignore*/
				}
			}
		}

		public static void close(ResultSet resultSet) {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					/*log or print or ignore*/
				}
			}
		}
	

public static byte[] serialize(Object obj) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ObjectOutputStream os = new ObjectOutputStream(out);
    os.writeObject(obj);
    return out.toByteArray();
}

public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
    ByteArrayInputStream in = new ByteArrayInputStream(data);
    ObjectInputStream is = new ObjectInputStream(in);
    return is.readObject();
}


}
