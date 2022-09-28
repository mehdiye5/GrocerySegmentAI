package com.sfu.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sfu.logger.ActivityLogger2;

public class DatabaseHelper {
	public static Connection connectToLocalDatabase() {
		Connection connection = null;
		

		
		String uname = "root";
		
		//String pass = "cP:A3GGo&a6A";
		//String pass = "root";

		//String pass = "080599";
		//String pass = "cP:A3GGo&a6A";

		//String pass = "nJ:K3LEo&a6K";
		String pass = "123456789";


		

		
		String url = "jdbc:mysql://localhost:3306/instacart?serverTimezone=EST&useSSL=false";
		//String url = "jdbc:oracle:thin:@localhost:1521:xe";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			ActivityLogger2.error(e);
		}
		try {
			connection = DriverManager.getConnection(url, uname, pass);
		} catch (SQLException e) {
			
			e.printStackTrace();
			ActivityLogger2.error(e);
		}
		return connection;
	}
	
	public static void closeQuietly(Connection connection) {
		if (connection != null) {
	        try {
	        	connection.close();
	        } catch (SQLException e) {
	        	System.out.println("Issue with Clossing the Connection");
	        	//ActivityLogger.debug("DatabaseHelper closeQuitely", "Closing Connection");
	        	System.out.println(e);
	        }
	    }
	}
	
	public static void closeQuietly(ResultSet resultSet) {
		if (resultSet != null) {
	        try {
	        	resultSet.close();
	        } catch (SQLException e) {
	        	System.out.println("Issue with Clossing the ResultSet");
	        	//ActivityLogger.debug("DatabaseHelper closeQuitely", "Closing resultSet");
	        	System.out.println(e);
	        }
	    }
	}

	public static void closeQuietly(PreparedStatement statement) {
		if (statement != null) {
	        try {
	        	statement.close();
	        } catch (SQLException e) {
	        	System.out.println("Issue with Clossing the PreparedStatement");
	        	//ActivityLogger.debug("DatabaseHelper closeQuitely", "Closing preparedStatement");
	        	System.out.println(e);
	        }
	    }
	}
}
