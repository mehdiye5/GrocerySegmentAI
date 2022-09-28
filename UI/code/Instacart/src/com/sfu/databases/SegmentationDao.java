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
import com.sfu.object.Segmentation;
import com.sfu.object.UserDataObject;

public class SegmentationDao {
	public static List<Segmentation> getAllSegmentation() {
		String sql = "SELECT * FROM segmentation;";

		Connection connection = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		List<Segmentation> objectList = new ArrayList<Segmentation>();

		try {
			
			connection = DatabaseHelper.connectToLocalDatabase();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				
				
				Integer id = resultSet.getInt("_id");
				Integer status = resultSet.getInt("status");
				Integer userDataObjectId = resultSet.getInt("user_data_object_id");
				UserDataObject userDataObject = UserDataObjectDao.getUserDataObjectById(userDataObjectId);
				String productId = resultSet.getString("product_id");
				String destinationBucket = resultSet.getString("destination_bucket");
				String destinationObject = resultSet.getString("destination_object");
				String responseId = resultSet.getString("response_id");
				String segmentationName = resultSet.getString("segmentation_name");
				
				Segmentation tempObject = new Segmentation(id, userDataObject, productId, destinationBucket, destinationObject, responseId, status, segmentationName);
				
				
			
				
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
	
	public static List<Segmentation> getAllSegmentationByUserDataId(int userObjectId) {
		String sql = "SELECT * FROM segmentation WHERE user_data_object_id = ?;";

		Connection connection = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		List<Segmentation> objectList = new ArrayList<Segmentation>();

		try {
			
			connection = DatabaseHelper.connectToLocalDatabase();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, userObjectId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				
				
				Integer id = resultSet.getInt("_id");
				Integer status = resultSet.getInt("status");
				Integer userDataObjectId = resultSet.getInt("user_data_object_id");
				UserDataObject userDataObject = UserDataObjectDao.getUserDataObjectById(userDataObjectId);
				String productId = resultSet.getString("product_id");
				String destinationBucket = resultSet.getString("destination_bucket");
				String destinationObject = resultSet.getString("destination_object");
				String responseId = resultSet.getString("response_id");
				String segmentationName = resultSet.getString("segmentation_name");
				
				Segmentation tempObject = new Segmentation(id, userDataObject, productId, destinationBucket, destinationObject, responseId, status, segmentationName);
				
				
			
				
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
	
	
	
	public static Segmentation getSegmentationById(int objectId) {
		String sql = "SELECT * FROM segmentation WHERE _id = ?;";

		Connection connection = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		Segmentation tempObject = new Segmentation();

		try {
			
			connection = DatabaseHelper.connectToLocalDatabase();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, objectId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				
				
				Integer id = resultSet.getInt("_id");
				Integer status = resultSet.getInt("status");
				Integer userDataObjectId = resultSet.getInt("user_data_object_id");
				UserDataObject userDataObject = UserDataObjectDao.getUserDataObjectById(userDataObjectId);
				String productId = resultSet.getString("product_id");
				String destinationBucket = resultSet.getString("destination_bucket");
				String destinationObject = resultSet.getString("destination_object");
				String responseId = resultSet.getString("response_id");
				String segmentationName = resultSet.getString("segmentation_name");
				
				tempObject = new Segmentation(id, userDataObject, productId, destinationBucket, destinationObject, responseId, status, segmentationName);
				
				
			
				
				
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
	
	
	  public static int insertSegmentation(Segmentation object) throws DatabaseInsertException {
	  
	  String sql = "INSERT INTO segmentation (user_data_object_id, product_id, destination_bucket, destination_object, response_id, segmentation_name) VALUES (?, ?, ?, ?, ?, ?);";
	  
	  Connection connection = null; PreparedStatement preparedStatement = null;
	  ResultSet uniqueKey = null;
	  
	  int id = 0;
	  
	  try {
	  
	  
	  connection = DatabaseHelper.connectToLocalDatabase(); preparedStatement =
	  connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	  
	  
	  preparedStatement.setInt(1, object.getDataObject().get_id());
	  preparedStatement.setString(2, object.getProductId());
	  preparedStatement.setString(3, object.getDestinationBucket());
	  preparedStatement.setString(4, object.getDestinationObject());
	  preparedStatement.setString(5, object.getResponseId());
	  preparedStatement.setString(6, object.getSegmentationName());
	  
	  
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
	  
	  public static void updateSegmentationStatus(Segmentation segmentation) {
			
			String sql = "UPDATE segmentation SET status = ? WHERE _id = ?;";
			
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			
			try {
				connection = DatabaseHelper.connectToLocalDatabase();
				preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				preparedStatement.setInt(1, segmentation.getStatus());			
				preparedStatement.setInt(2, segmentation.get_id());
				
				preparedStatement.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println("Issue with client");
				// ActivityLogger.debug("RequestDao insertRequest", "Connection is Null");
				ActivityLogger.debug("ClientDao: updateCLient Client: " + e.getMessage(), "Connection is Null "  + e.getMessage());
				System.out.println(e);
				
			} finally {
				DatabaseHelper.closeQuietly(preparedStatement);
				DatabaseHelper.closeQuietly(connection);
			}
		}
	  
	  public static void updateSegmentationResponseId(Segmentation segmentation) {
			
			String sql = "UPDATE segmentation SET response_id = ? WHERE _id = ?;";
			
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			
			try {
				connection = DatabaseHelper.connectToLocalDatabase();
				preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				preparedStatement.setString(1, segmentation.getResponseId());			
				preparedStatement.setInt(2, segmentation.get_id());
				
				preparedStatement.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println("Issue with client");
				// ActivityLogger.debug("RequestDao insertRequest", "Connection is Null");
				ActivityLogger.debug("ClientDao: updateCLient Client: " + e.getMessage(), "Connection is Null "  + e.getMessage());
				System.out.println(e);
				
			} finally {
				DatabaseHelper.closeQuietly(preparedStatement);
				DatabaseHelper.closeQuietly(connection);
			}
		}
}
