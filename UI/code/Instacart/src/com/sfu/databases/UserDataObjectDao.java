package com.sfu.databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sfu.exception.DatabaseInsertException;
import com.sfu.logger.ActivityLogger;
import com.sfu.object.UserDataObject;

public class UserDataObjectDao {
	public static List<UserDataObject> getAllUserDataObject() {
		String sql = "SELECT * FROM user_data_object;";

		Connection connection = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		List<UserDataObject> objectList = new ArrayList<UserDataObject>();

		try {
			
			connection = DatabaseHelper.connectToLocalDatabase();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				
				
				Integer id = resultSet.getInt("_id");
				String name = resultSet.getString("name");
				Integer userId = resultSet.getInt("user_id");
				
				UserDataObject tempObject = new UserDataObject(id, name, userId);
				
				
			
				
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
	
	public static UserDataObject getUserDataObjectById(int objectId) {
		String sql = "SELECT * FROM user_data_object WHERE _id = ?;";

		Connection connection = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		UserDataObject tempObject = new UserDataObject();

		try {
			
			connection = DatabaseHelper.connectToLocalDatabase();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, objectId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				
				
				int id = resultSet.getInt("_id");
				String name = resultSet.getString("name");
				Integer userId = resultSet.getInt("user_id");
				
				tempObject = new UserDataObject(id, name, userId);
				
				
			
				
				
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
	
	
	  public static int insertUserDataObject(UserDataObject object) throws DatabaseInsertException {
	  
	  String sql = "INSERT INTO user_data_object (name, user_id) VALUES (?, ?);";
	  
	  Connection connection = null; PreparedStatement preparedStatement = null;
	  ResultSet uniqueKey = null;
	  
	  int id = 0;
	  
	  try {
	  
	  
	  connection = DatabaseHelper.connectToLocalDatabase(); preparedStatement =
	  connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	  
	  
	  preparedStatement.setString(1, object.getObjectName());
	  preparedStatement.setInt(2, object.getUserId());
	  
	  id = preparedStatement.executeUpdate();
	  
	  if (id > 0) { uniqueKey = preparedStatement.getGeneratedKeys();
	  
	  if (uniqueKey.next()) { id = uniqueKey.getInt(1); } }
	  
	  
	  
	  
	  
	  } catch (SQLException e) { //ActivityLogger.error(e);
	  System.out.println("Insert client" + e.getMessage());
	  ActivityLogger.debug("ClientDao: insertClient client", "Connection is Null "
	  + e.getMessage());
	  
	  } finally { DatabaseHelper.closeQuietly(uniqueKey);
	  DatabaseHelper.closeQuietly(preparedStatement);
	  DatabaseHelper.closeQuietly(connection); }
	  
	  if (id > 0) { return id; }
	  
	  throw new DatabaseInsertException("InsertUser"); 
	  
	  }
	 

}
