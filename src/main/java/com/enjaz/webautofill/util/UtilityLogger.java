/**
 * UtilityLogger.java
 **/

package com.enjaz.webautofill.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtilityLogger {

	
	private String classname ="";
	private String infoFilePath;
	private String errorFilePath;
	private String debugFilePath;
	public UtilityLogger() {

	}

	public UtilityLogger(String className) {
		this.classname = className;
	}
	
	private String getDriverPath()
	{
		try
		{
			String logPath =  new File(".").getCanonicalPath() + "\\log";
			File file = new File(logPath);
			if(!file.exists())
			{
				file.mkdirs();
			}
			return logPath;
		
		}catch(IOException ex){}
		return "";
	}

	public void error(String Message, Exception ex) {
		Logger logger = Logger.getLogger(this.classname);
		FileHandler fileHandler = null;
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			logger.setUseParentHandlers(false);
			this.errorFilePath = getDriverPath() + "\\logErr.log";
			String path = this.errorFilePath;
			fileHandler = new FileHandler(path, true);
			fileHandler.setFormatter(new LogFormatter());
			fileHandler.setLevel(Level.INFO);
			logger.addHandler(fileHandler);

			StackTraceElement[] stack = ex.getStackTrace();
			String exceptionAsString = "";
			for (StackTraceElement line : stack) {
				exceptionAsString += line.toString() + "\n";
			}
			logger.info(Message + " : " + exceptionAsString);

		} catch (Exception exception) {
			logger.info(exception.toString());
		} finally {
			if (fileHandler != null)
				fileHandler.close();
			if (sw != null)
				try {
					sw.close();
				} catch (IOException e) {
					logger.info(ex.toString());
				}
			if (pw != null)
				pw.close();
		}
	}
	
	public void info(String Message) {
		Logger logger = Logger.getLogger(this.classname);
		FileHandler fileHandler = null;
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			logger.setUseParentHandlers(false);
			this.infoFilePath = getDriverPath() + "\\logInfo.log";
			String path = this.infoFilePath;
			fileHandler = new FileHandler(path, true);
			fileHandler.setFormatter(new LogFormatter());
			fileHandler.setLevel(Level.INFO);
			logger.addHandler(fileHandler);			
			logger.info(Message);

		} catch (Exception exception) {
			logger.info(exception.toString());
		} finally {
			if (fileHandler != null)
				fileHandler.close();
			if (sw != null)
				try {
					sw.close();
				} catch (IOException e) {
					logger.info(e.toString());
				}
			if (pw != null)
				pw.close();
		}
	}
	public void debug(String Message) {
		Logger logger = Logger.getLogger(this.classname);
		FileHandler fileHandler = null;
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			logger.setUseParentHandlers(false);
			this.debugFilePath = getDriverPath() + "\\logDebug.log";
			String path = this.debugFilePath;
			fileHandler = new FileHandler(path, true);
			fileHandler.setFormatter(new LogFormatter());
			fileHandler.setLevel(Level.ALL);
			logger.addHandler(fileHandler);			
			logger.info(Message);

		} catch (Exception exception) {
			logger.info(exception.toString());
		} finally {
			if (fileHandler != null)
				fileHandler.close();
			if (sw != null)
				try {
					sw.close();
				} catch (IOException e) {
					logger.info(e.toString());
				}
			if (pw != null)
				pw.close();
		}
	}
}
