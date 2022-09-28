package com.sfu.databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



import com.sfu.databases.DatabaseHelper;
//import com.sfu.exception.DatabaseInsertException;
import com.sfu.logger.ActivityLogger;
import com.sfu.object.DataObjectType;



public class DataObjectTypeDao {
	public static List<DataObjectType> getAllDataObjectTypes() {
		String sql = "SELECT * FROM data_object_type;";

		Connection connection = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		List<DataObjectType> objectList = new ArrayList<DataObjectType>();

		try {
			
			connection = DatabaseHelper.connectToLocalDatabase();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				
				
				int id = resultSet.getInt("_id");
				String name = resultSet.getString("name");
				
				DataObjectType tempObject = new DataObjectType(id, name);
				
				
			
				
				objectList.add(tempObject);
			}
		} catch (SQLException e) {
			System.out.println("Issue with getuserList");
			ActivityLogger.debug("UserDao: getAllUsers", e.getMessage());
			//System.out.println(e);

		} finally {
			DatabaseHelper.closeQuietly(preparedStatement);
			DatabaseHelper.closeQuietly(connection);
			
		}
		
		return objectList;
	}
	
	public static DataObjectType getDataObjectTypeById(int objectId) {
		String sql = "SELECT * FROM data_object_type WHERE _id = ?;";

		Connection connection = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		DataObjectType tempObject = new DataObjectType();

		try {
			
			connection = DatabaseHelper.connectToLocalDatabase();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, objectId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				
				
				int id = resultSet.getInt("_id");
				String name = resultSet.getString("name");
				
				tempObject = new DataObjectType(id, name);
				
				
			
				
				
			}
		} catch (SQLException e) {
			System.out.println("Issue with getuserList");
			ActivityLogger.debug("UserDao: getAllUsers", e.getMessage());
			//System.out.println(e);

		} finally {
			DatabaseHelper.closeQuietly(preparedStatement);
			DatabaseHelper.closeQuietly(connection);
			
		}
		
		return tempObject;
	}
	
	/*
	 * public static int insertUser(User user) throws DatabaseInsertException {
	 * 
	 * String sql = "INSERT INTO user (name, email, password) VALUES (?, ?, ?);";
	 * 
	 * Connection connection = null; PreparedStatement preparedStatement = null;
	 * ResultSet uniqueKey = null;
	 * 
	 * int id = 0;
	 * 
	 * try {
	 * 
	 * 
	 * connection = DatabaseHelper.connectToLocalDatabase(); preparedStatement =
	 * connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	 * 
	 * 
	 * preparedStatement.setString(1, user.getName());
	 * preparedStatement.setString(2, user.getEmail());
	 * preparedStatement.setString(3, user.getPassword());
	 * 
	 * id = preparedStatement.executeUpdate();
	 * 
	 * if (id > 0) { uniqueKey = preparedStatement.getGeneratedKeys();
	 * 
	 * if (uniqueKey.next()) { id = uniqueKey.getInt(1); } }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * } catch (SQLException e) { //ActivityLogger.error(e);
	 * System.out.println("Insert client" + e.getMessage());
	 * ActivityLogger.debug("ClientDao: insertClient client", "Connection is Null "
	 * + e.getMessage());
	 * 
	 * } finally { DatabaseHelper.closeQuietly(uniqueKey);
	 * DatabaseHelper.closeQuietly(preparedStatement);
	 * DatabaseHelper.closeQuietly(connection); }
	 * 
	 * if (id > 0) { return id; }
	 * 
	 * throw new DatabaseInsertException("InsertUser"); }
	 */

}
