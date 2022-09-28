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
import com.sfu.object.DataObjectType;
import com.sfu.object.InstacartDataObject;
import com.sfu.object.UserDataObject;

public class InstacartDataObjectDao {
	public static List<InstacartDataObject> getAllInstacartDataObject() {
		String sql = "SELECT * FROM instacart_data_object;";

		Connection connection = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		List<InstacartDataObject> objectList = new ArrayList<InstacartDataObject>();

		try {
			
			connection = DatabaseHelper.connectToLocalDatabase();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				
				
				Integer id = resultSet.getInt("_id");
				int userDataId = resultSet.getInt("user_data_id");
				Integer objectTypeId = resultSet.getInt("object_type_id");
				String bucketName = resultSet.getString("bucket_name");
				String bucketKey = resultSet.getString("bucket_key");
				
				UserDataObject userData = UserDataObjectDao.getUserDataObjectById(userDataId);
				DataObjectType objectType = DataObjectTypeDao.getDataObjectTypeById(objectTypeId);
				
				InstacartDataObject tempObject = new InstacartDataObject(id, userData , objectType, bucketName, bucketKey);
				
				tempObject.setInstacartFeatures(InstacartFeaturesDao.getAllInstacartFeaturesByInstacartId(id));
				
			
				
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
	
	public static InstacartDataObject getInstacartDataObjectById(int objectId) {
		String sql = "SELECT * FROM instacart_data_object WHERE _id = ?;";

		Connection connection = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		InstacartDataObject tempObject = new InstacartDataObject();

		try {
			
			connection = DatabaseHelper.connectToLocalDatabase();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, objectId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				
				
				Integer id = resultSet.getInt("_id");
				int userDataId = resultSet.getInt("user_data_id");
				Integer objectTypeId = resultSet.getInt("object_type_id");
				String bucketName = resultSet.getString("bucket_name");
				String bucketKey = resultSet.getString("bucket_key");
				
				UserDataObject userData = UserDataObjectDao.getUserDataObjectById(userDataId);
				DataObjectType objectType = DataObjectTypeDao.getDataObjectTypeById(objectTypeId);
				
				tempObject = new InstacartDataObject(id, userData , objectType, bucketName, bucketKey);
				
				tempObject.setInstacartFeatures(InstacartFeaturesDao.getAllInstacartFeaturesByInstacartId(id));		
				
				
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
	
	
	  public static int insertInstacartDataObject(InstacartDataObject object) throws DatabaseInsertException {
	  
	  String sql = "INSERT INTO instacart_data_object (user_data_id, object_type_id, bucket_name, bucket_key) VALUES (?, ?, ?, ?);";
	  
	  Connection connection = null; PreparedStatement preparedStatement = null;
	  ResultSet uniqueKey = null;
	  
	  int id = 0;
	  
	  try {
	  
	  
	  connection = DatabaseHelper.connectToLocalDatabase(); preparedStatement =
	  connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	  
	  preparedStatement.setInt(1, object.getUserData().get_id());
	  preparedStatement.setInt(2, object.getObjectType().get_id());
	  preparedStatement.setString(3, object.getBucketName());
	  preparedStatement.setString(4, object.getBucketKey());
	  
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
