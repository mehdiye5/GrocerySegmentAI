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
import com.sfu.object.TrainInstacart;
import com.sfu.object.UserDataObject;

public class TrainInstacartDao {
	public static List<TrainInstacart> getAllTrainInstacart() {
		String sql = "SELECT * FROM train_instacart;";

		Connection connection = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		List<TrainInstacart> objectList = new ArrayList<TrainInstacart>();

		try {
			
			connection = DatabaseHelper.connectToLocalDatabase();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				
				
				Integer id = resultSet.getInt("_id");
				Integer status = resultSet.getInt("status");				
				int userDataObjectId = resultSet.getInt("user_data_object_id");
				String trainId = resultSet.getString("train_id");
				UserDataObject dataObject = UserDataObjectDao.getUserDataObjectById(userDataObjectId);
				
				TrainInstacart tempObject = new TrainInstacart(id, status, dataObject, trainId);
				
				
			
				
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
	
	
	
	public static TrainInstacart getTrainInstacartById(int objectId) {
		String sql = "SELECT * FROM train_instacart WHERE _id = ?;";

		Connection connection = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		TrainInstacart tempObject = new TrainInstacart();

		try {
			
			connection = DatabaseHelper.connectToLocalDatabase();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, objectId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				
				
				Integer id = resultSet.getInt("_id");
				Integer status = resultSet.getInt("status");				
				int userDataObjectId = resultSet.getInt("user_data_object_id");
				String trainId = resultSet.getString("train_id");
				UserDataObject dataObject = UserDataObjectDao.getUserDataObjectById(userDataObjectId);
				
				tempObject = new TrainInstacart(id, status, dataObject, trainId);
				
				
			
				
				
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
	
	public static TrainInstacart getTrainInstacartByUserDataId(int objectId) {
		String sql = "SELECT * FROM train_instacart WHERE user_data_object_id = ?;";

		Connection connection = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		TrainInstacart tempObject = new TrainInstacart();

		try {
			
			connection = DatabaseHelper.connectToLocalDatabase();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, objectId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				
				
				Integer id = resultSet.getInt("_id");
				Integer status = resultSet.getInt("status");				
				int userDataObjectId = resultSet.getInt("user_data_object_id");
				String trainId = resultSet.getString("train_id");
				UserDataObject dataObject = UserDataObjectDao.getUserDataObjectById(userDataObjectId);
				
				tempObject = new TrainInstacart(id, status, dataObject, trainId);
				
				
			
				
				
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
	
	
	  public static int insertTrainInstacart(TrainInstacart object) throws DatabaseInsertException {
	  
	  String sql = "INSERT INTO train_instacart (status, user_data_object_id) VALUES (?, ?);";
	  
	  Connection connection = null; PreparedStatement preparedStatement = null;
	  ResultSet uniqueKey = null;
	  
	  int id = 0;
	  
	  try {
	  
	  
	  connection = DatabaseHelper.connectToLocalDatabase(); preparedStatement =
	  connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	  
	  
	  preparedStatement.setInt(1, object.getStatus());
	  preparedStatement.setInt(2, object.getDataObject().get_id());
	  
	  
	  
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
	  
	  public static void updateTrainInstacartStatus(TrainInstacart trainObject) {
			
			String sql = "UPDATE train_instacart SET status = ?, train_id = ? WHERE _id = ?;";
			
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			
			try {
				connection = DatabaseHelper.connectToLocalDatabase();
				preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				preparedStatement.setInt(1, trainObject.getStatus());
				preparedStatement.setString(2, trainObject.getTrainId());
				preparedStatement.setInt(3, trainObject.get_id());
				
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
