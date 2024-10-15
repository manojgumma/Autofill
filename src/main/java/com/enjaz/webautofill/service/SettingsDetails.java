package com.enjaz.webautofill.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjaz.webautofill.util.PropertyReader;
import com.enjaz.webautofill.valueobject.SettingsVO;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

/**
 * 17-04-2015
 * @author Prabakaran
 *
 */
@Service
public class SettingsDetails {
	@Autowired
	private PropertyReader propertyReader;
	private static final Logger logger = LoggerFactory.getLogger(SettingsDetails.class);
	//private ClassLoader classLoader = getClass().getClassLoader();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("welcome to settings");
		//SettingsDetails sett = new SettingsDetails();
		//String opsysEndpoint = "http://10.96.45.12:8080/VFSEtimad";
	}
	
	/**
	 * 
	 * @return SettingsVO
	 */
	public SettingsVO readConfig() {
		JsonParser jsonParser = new JsonParser();
		SettingsVO settings = null;
		try{
			String filePath = new File(".").getCanonicalPath()+"\\"+propertyReader.getConfigFile();
			if(!(filePath.trim().equals(""))) {
				Object obj = jsonParser.parse(new FileReader(filePath));
				settings = new Gson().fromJson(new Gson().toJson(obj), SettingsVO.class);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		return settings;
	}
	
	/**
	 * 
	 * @param settingsVO
	 * @throws IOException
	 */
	public void createConfig(SettingsVO settingsVO) throws IOException {
		FileWriter writter = null;
		File file = null;
		try{
			String filePath = new File(".").getCanonicalPath()+"\\"+propertyReader.getConfigFile();
			if(!(filePath.trim().equals(""))) {
				file = new File(filePath);
				file.createNewFile();
				String conficString = new Gson().toJson(settingsVO);
				writter = new FileWriter(file);
				writter.write(conficString);
				writter.flush();
				writter.close();
				logger.info("Settings File is created!");
			}
		} finally {
			writter.close();
		}
	}

}
