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
import com.sfu.object.InstacartDataObject;
import com.sfu.object.InstacartFeatures;
import com.sfu.object.InstacartFeatures;

public class InstacartFeaturesDao {
	public static List<InstacartFeatures> getAllInstacartFeatures() {
		String sql = "SELECT * FROM instacart_features;";

		Connection connection = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		List<InstacartFeatures> objectList = new ArrayList<InstacartFeatures>();

		try {
			
			connection = DatabaseHelper.connectToLocalDatabase();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				
				
				Integer id = resultSet.getInt("_id");
				Integer instacartObjectId = resultSet.getInt("instacart_object_id");				
				String featureSource = resultSet.getString("feature_source");
				String featureName = resultSet.getString("feature_name");
				
				InstacartFeatures tempObject = new InstacartFeatures(id, instacartObjectId, featureSource, featureName);
				
				
			
				
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
	
	public static List<InstacartFeatures> getAllInstacartFeaturesByInstacartId(int instacartId) {
		String sql = "SELECT * FROM instacart_features WHERE instacart_object_id = ?;";

		Connection connection = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		List<InstacartFeatures> objectList = new ArrayList<InstacartFeatures>();

		try {
			
			connection = DatabaseHelper.connectToLocalDatabase();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, instacartId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				
				
				Integer id = resultSet.getInt("_id");
				Integer instacartObjectId = resultSet.getInt("instacart_object_id");
				
				String featureSource = resultSet.getString("feature_source");
				String featureName = resultSet.getString("feature_name");
				
				InstacartFeatures tempObject = new InstacartFeatures(id, instacartObjectId, featureSource, featureName);
				
				
			
				
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
	
	public static InstacartFeatures getInstacartFeaturesById(int objectId) {
		String sql = "SELECT * FROM instacart_features WHERE _id = ?;";

		Connection connection = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		InstacartFeatures tempObject = new InstacartFeatures();

		try {
			
			connection = DatabaseHelper.connectToLocalDatabase();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, objectId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				
				
				Integer id = resultSet.getInt("_id");
				Integer instacartObjectId = resultSet.getInt("instacart_object_id");
				
				String featureSource = resultSet.getString("feature_source");
				String featureName = resultSet.getString("feature_name");
				
				tempObject = new InstacartFeatures(id, instacartObjectId, featureSource, featureName);
				
				
			
				
				
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
	
	
	  public static int insertInstacartFeatures(InstacartFeatures object) throws DatabaseInsertException {
	  
	  String sql = "INSERT INTO instacart_features (instacart_object_id, feature_source, feature_name) VALUES (?, ?, ?);";
	  
	  Connection connection = null; PreparedStatement preparedStatement = null;
	  ResultSet uniqueKey = null;
	  
	  int id = 0;
	  
	  try {
	  
	  
	  connection = DatabaseHelper.connectToLocalDatabase(); preparedStatement =
	  connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	  
	  
	  preparedStatement.setInt(1, object.getDataObject());
	  preparedStatement.setString(2, object.getFeatureSource());
	  preparedStatement.setString(3, object.getFeatureName());
	  
	  
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
