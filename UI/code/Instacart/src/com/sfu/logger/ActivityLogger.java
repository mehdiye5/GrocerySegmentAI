package com.sfu.logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sfu.databases.DatabaseHelper;

/**
 * ActivityLogger is a custom logger.
 * <p>
 * It uses two files for logging. INFO and WARN messages go to the
 * infoslareportutility.log file. ALL messages (ERROR, DEBUG, INFO, WARN) go to
 * debugslareportutility.log file.
 * 
 * TODO Implement a rolling file strategy based on file size or time.
 * 
 */
public class ActivityLogger {
	
	public static void info(String msg) {
		new Thread(() -> {
			String type = "Info";

			Date dateNow = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String message = "INFO - " + msg + ".";
			//writeInfo(message, type, dateFormat.format(dateNow));

			// extra space
			type = "Debug";
			message = "INFO  - " + msg + ".";
			//writeInfo(message, type, dateFormat.format(dateNow));
		}).start();
	}

	public static void info(String user, String msg) {
		new Thread(() -> {
			String type = "Info";
			Date dateNow = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String message = "INFO - " +"User \"" + user + "\" " + msg + ".";
			//writeInfo(message, type,  dateFormat.format(dateNow));

			// extra space
			type = "Debug";
			message = "INFO  - " + "User \"" + user + "\" " + msg + ".";
			//writeInfo(message, type,  dateFormat.format(dateNow));

		}).start();
	}

	public static void warn(String msg) {
		new Thread(() -> {
			String type = "Info";
			Date dateNow = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String message = "WARN - " + msg + ".";
			//writeInfo(message, type,  dateFormat.format(dateNow));


			// extra space
			type = "Debug";
			message = "WARN  - " + msg + ".";
			writeInfo(message, type,  dateFormat.format(dateNow));
		}).start();
	}

	public static void warn(String user, String msg) {
		new Thread(() -> {
			String type = "Info";
			Date dateNow = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String message = "WARN - " + "User \"" + user + "\" " + msg + ".";
			//writeInfo(message, type,  dateFormat.format(dateNow));

			// extra space
			type = "Debug";
			message = "WARN  - " + "User \"" + user + "\" " + msg + ".";
			writeInfo(message, type,  dateFormat.format(dateNow));
		}).start();
	}

	public static void debug(String msg) {
		new Thread(() -> {
			String type = "Debug";
			Date dateNow = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String message = "DEBUG - " + msg + ".";
			
			writeInfo(message, type,  dateFormat.format(dateNow));
		}).start();
	}

	public static void debug(String location, String msg) {
		new Thread(() -> {
			String type = "Debug";
			Date dateNow = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String message = "DEBUG - " + "(" + location + "): " + msg + ".";
			
			writeInfo(message, type,  dateFormat.format(dateNow));
		}).start();
	}

	public static void debug(String user, String location, String msg) {
		new Thread(() -> {
			String type = "Debug";
			Date dateNow = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String message = "DEBUG - " + "User \"" + user + "\" (" + location + "): "
					+ msg + ".";
			writeInfo(message, type,  dateFormat.format(dateNow));
		}).start();
	}

	public static void error(String msg) {
		new Thread(() -> {
			String type = "Debug";
			Date dateNow = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String message = "ERROR - " + msg + ".";
			writeInfo(message, type,  dateFormat.format(dateNow));
		}).start();
	}

	public static void error(String location, String msg) {
		new Thread(() -> {
			String type = "Debug";
			Date dateNow = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String message = "ERROR - "+ "(" + location + "): " + msg + ".";
			writeInfo(message, type,  dateFormat.format(dateNow));
		}).start();
	}

	public static void error(String user, String location, String msg) {
		new Thread(() -> {
			String type = "Debug";
			Date dateNow = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String message = "ERROR - " + "User \"" + user + "\" (" + location + "): "
					+ msg + ".";
			writeInfo(message, type,  dateFormat.format(dateNow));
		}).start();
	}

	public static void error(Exception e) {
		new Thread(() -> {
			String type = "Debug";
			Date dateNow = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String className = "";
			String methodName = "";
			String fileName = "";

			int lineNumber = 0;

			for (StackTraceElement element : e.getStackTrace()) {
				if (element.getClassName().startsWith("com.cgi")) {
					className = element.getClassName();
					methodName = element.getMethodName();
					fileName = element.getFileName();
					lineNumber = element.getLineNumber();
					break;
				}
			}

			String message = e.toString();
			String output = "ERROR - " + "(" + className + "." + methodName;
			
			if (fileName != null && !fileName.isEmpty()) {
				output += "(" + fileName;
				if (lineNumber > 0) {
					output += ":" + lineNumber;
				}
				output += ")";
			}
			
			output += "): " + message + ".";
			writeInfo(output, type,  dateFormat.format(dateNow));
			
			//String stackTrace = Arrays.toString(e.getStackTrace());
			
			//output = "Stack trace: " + stackTrace;
			//writeInfo(output, type,  dateFormat.format(dateNow));
		}).start();
	}

	private static synchronized void writeInfo(String msg, String type, String date) {
		String sql = "INSERT INTO activities (message, type, entry_date) VALUES (?, ?, ?)";

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = DatabaseHelper.connectToLocalDatabase();
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			System.out.println(msg);
			preparedStatement.setString(1, msg);
			preparedStatement.setString(2, type);
			preparedStatement.setString(3, date);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e + "Issue with writeInfor");

		} finally {
			DatabaseHelper.closeQuietly(preparedStatement);
			DatabaseHelper.closeQuietly(connection);
			
		}

	}

}
