package com.acme.credvarejo.ado.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLUtil {

	private static Connection connection = null;

	public static Connection openConnection() throws Exception {
		if (connection != null) {
			if (!connection.isClosed()) {
				return connection;
			}

			closeConnection();
		}

		try {
			Class.forName("com.mysql.jdbc.Driver");

			connection = DriverManager.getConnection("jdbc:mysql://localhost/credvarejo?user=root&password=");
		} catch (Exception e) {
			throw e;
		}

		return connection;
	}

	public static void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {}
	}

}
