package com.sfu.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/** 
 * ActivityLogger is a custom logger.
 * 
 * It uses two files for logging.
 * INFO and WARN messages go to the infoas400webapp.log file.
 * ALL messages (ERROR, DEBUG, INFO, WARN) go to debugas400webapp.log file.
 * 
 * TODO Implement a rolling file strategy based on file size or time.
 * 
 */

public class ActivityLogger2 {
	
	private static final String INFO_DIR_PATH = "/usr/tomcat/logs/as400webapplogs";
	private static final String INFO_FILE_PATH = "/usr/tomcat/logs/as400webapplogs/infoas400webapp.log";
	private static final String DEBUG_DIR_PATH = "/usr/tomcat/logs/as400webapplogs";
	private static final String DEBUG_FILE_PATH = "/usr/tomcat/logs/as400webapplogs/debugas400webapp.log";
	
	public static void info(String msg) {
    	new Thread(() -> {
    		Date dateNow = new Date();
    		SimpleDateFormat dateFormat = new SimpleDateFormat ("MMM dd, yyyy HH:mm:ss");
    		String message = "INFO - " + dateFormat.format(dateNow) + " - " + msg + ".";
    		writeInfo(message);
    		// extra space
    		message = "INFO  - " + dateFormat.format(dateNow) + " - " + msg + ".";
    		writeDebug(message);
    	}).start();
    }
	
    public static void info(String user, String msg) {
    	new Thread(() -> {
    		Date dateNow = new Date();
    		SimpleDateFormat dateFormat = new SimpleDateFormat ("MMM dd, yyyy HH:mm:ss");
    		String message = "INFO - " + dateFormat.format(dateNow) + " - User \"" + user + "\" " + msg + ".";
    		writeInfo(message);
    		// extra space
    		message = "INFO  - " + dateFormat.format(dateNow) + " - User \"" + user + "\" " + msg + ".";
    		writeDebug(message);
    	}).start();
    }
    
   
    public static void signOff(String user, String msg) {
    	new Thread(() -> {    		
    		
    		Date datenow = new Date();
    		
    		// used to parse the date from the msg
    		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
    		// used to formate the date for logs
    		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
    		SimpleDateFormat timeFormat =  new SimpleDateFormat("HH:mm:ss");
    		
    		// converted date from parsed msg
    		Date convertedDate = null;
    		    		
    		//parse msg
    		try {
				convertedDate = parser.parse(msg);
			} catch (ParseException e) {
				ActivityLogger2.error("ViewLogsController.doGet()", e.toString());
		    	ActivityLogger2.error(Arrays.toString(e.getStackTrace()));
			}   		
    		
    		String message = "Batch Cycle Sign-Off - " + dateFormat.format(convertedDate) + " " +  timeFormat.format(datenow) + " - User \"" + user + "\" "  + ".";
    		writeInfo(message);
    		// extra space
    		message = "Batch Cycle Sign-Off  - " + dateFormat.format(convertedDate) + " - User \"" + user + "\" " + ".";
    		writeDebug(message);
    	}).start();
    }
    
    public static void warn(String msg) {
    	new Thread(() -> {
    		Date dateNow = new Date();
    		SimpleDateFormat dateFormat = new SimpleDateFormat ("MMM dd, yyyy HH:mm:ss");
    		String message = "WARN - " + dateFormat.format(dateNow) + " - " + msg + ".";
    		writeInfo(message);
    		// extra space
    		message = "WARN  - " + dateFormat.format(dateNow) + " - " + msg + ".";
    		writeDebug(message);
    	}).start();
    }
    
    public static void warn(String user, String msg) {
    	new Thread(() -> {
    		Date dateNow = new Date();
    		SimpleDateFormat dateFormat = new SimpleDateFormat ("MMM dd, yyyy HH:mm:ss");
    		String message = "WARN - " + dateFormat.format(dateNow) + " - User \"" + user + "\" " + msg + ".";
    		writeInfo(message);
    		// extra space
    		message = "WARN  - " + dateFormat.format(dateNow) + " - User \"" + user + "\" " + msg + ".";
    		writeDebug(message);
    	}).start();
    }
    
    public static void debug(String msg) {
    	new Thread(() -> {
    		Date dateNow = new Date();
    		SimpleDateFormat dateFormat = new SimpleDateFormat ("MMM dd, yyyy HH:mm:ss");
    		String message = "DEBUG - " + dateFormat.format(dateNow) + " - " + msg + ".";
    		writeDebug(message);
    	}).start();
    }
    
    public static void debug(String location, String msg) {
    	new Thread(() -> {
    		Date dateNow = new Date();
    		SimpleDateFormat dateFormat = new SimpleDateFormat ("MMM dd, yyyy HH:mm:ss");
    		String message = "DEBUG - " + dateFormat.format(dateNow) + " - (" + location + "): " + msg + ".";
    		writeDebug(message);
    	}).start();
    }
    
    public static void debug(String user, String location, String msg) {
    	new Thread(() -> {
    		Date dateNow = new Date();
    		SimpleDateFormat dateFormat = new SimpleDateFormat ("MMM dd, yyyy HH:mm:ss");
    		String message = "DEBUG - " + dateFormat.format(dateNow) + " - User \"" + user + "\" (" + location + "): " + msg + ".";
    		writeDebug(message);
    	}).start();
    }
    
    public static void error(String msg) {
    	new Thread(() -> {
    		Date dateNow = new Date();
    		SimpleDateFormat dateFormat = new SimpleDateFormat ("MMM dd, yyyy HH:mm:ss");
    		String message = "ERROR - " + dateFormat.format(dateNow) + " - " + msg + ".";
    		writeDebug(message);
    	}).start();
    }
    
    public static void error(String location, String msg) {
    	new Thread(() -> {
    		Date dateNow = new Date();
    		SimpleDateFormat dateFormat = new SimpleDateFormat ("MMM dd, yyyy HH:mm:ss");
    		String message = "ERROR - " + dateFormat.format(dateNow) + " - (" + location + "): " + msg + ".";
    		writeDebug(message);
    	}).start();
    }
    
    public static void error(String user, String location, String msg) {
    	new Thread(() -> {
    		Date dateNow = new Date();
    		SimpleDateFormat dateFormat = new SimpleDateFormat ("MMM dd, yyyy HH:mm:ss");
    		String message = "ERROR - " + dateFormat.format(dateNow) + " - User \"" + user + "\" (" + location + "): " + msg + ".";
    		writeDebug(message);
    	}).start();
    }
    
    private static synchronized void writeInfo(String msg) {
    	File primeDirPath = new File(INFO_DIR_PATH);
    	if (primeDirPath.exists() && primeDirPath.isDirectory()) {
    		FileWriter fw = null;
    		BufferedWriter bw = null;
    		PrintWriter out = null;
    		File logFile = new File(INFO_FILE_PATH);
    		try {
    			// if file already exists will do nothing
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			} 
    		try {
    			fw = new FileWriter(INFO_FILE_PATH, true);
			    bw = new BufferedWriter(fw);
			    out = new PrintWriter(bw);
			    out.println(msg);
			    
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
		        if(out != null) {
		        	try {
		        		out.close();
		        	} catch (Exception e) {
		        		e.printStackTrace();
		        	}
		        }
		        if(bw != null) {
		        	try {
		        		bw.close();
		        	} catch (Exception e) {
		        		e.printStackTrace();
		        	}
		        }
		        if(fw != null) {
		        	try {
		        		fw.close();
		        	} catch (Exception e) {
		        		e.printStackTrace();
		        	}
		        }
			}
    	}
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
			
			ActivityLogger2.writeDebug(output);
			
			//String stackTrace = Arrays.toString(e.getStackTrace());
			
			//output = "Stack trace: " + stackTrace;
			//writeInfo(output, type,  dateFormat.format(dateNow));
		}).start();
	}
    
    private static synchronized void writeDebug(String msg) {
    	File primeDirPath = new File(DEBUG_DIR_PATH);
    	if (primeDirPath.exists() && primeDirPath.isDirectory()) {
    		FileWriter fw = null;
    		BufferedWriter bw = null;
    		PrintWriter out = null;
    		File logFile = new File(DEBUG_FILE_PATH);
    		try {
    			// if file already exists will do nothing
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			} 
    		try {
    			fw = new FileWriter(DEBUG_FILE_PATH, true);
			    bw = new BufferedWriter(fw);
			    out = new PrintWriter(bw);
			    out.println(msg);
			    
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
		        if(out != null) {
		        	try {
		        		out.close();
		        	} catch (Exception e) {
		        		e.printStackTrace();
		        	}
		        }
		        if(bw != null) {
		        	try {
		        		bw.close();
		        	} catch (Exception e) {
		        		e.printStackTrace();
		        	}
		        }
		        if(fw != null) {
		        	try {
		        		fw.close();
		        	} catch (Exception e) {
		        		e.printStackTrace();
		        	}
		        }
			}
    	}
    }
}
