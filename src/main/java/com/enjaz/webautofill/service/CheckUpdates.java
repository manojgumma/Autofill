package com.enjaz.webautofill.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//import com.enjaz.webautofill.valueobject.SettingsVO;
//import com.enjaz.webautofill.valueobject.UpdateURLVO;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

@Component
public class CheckUpdates {
	/*private static final Logger logger = LoggerFactory.getLogger(CheckUpdates.class);
	private String autoupdateURL;
	
	public String getUpgradeURL() {
		return autoupdateURL;
	}
	
	public void setUpgradeURL(String upgradeURL) {
		this.autoupdateURL = upgradeURL;
	}
	
	public void invokeAntBuild() throws IOException {
		//logger.info("Schedule is working!");
		String filePath = new File(".").getCanonicalPath()+"\\"+"updateurl_config.json";
		logger.info("autoupdate initiate");
		System.out.println(filePath);
		UpdateURLVO updateUrlVO = readConfig(filePath);
		UpdateList updateList = new UpdateList();
		for (int i=0;i<3;i++) {
			if(i == 0)
			{
			autoupdateURL = updateUrlVO.getRegion1();
			}
			if(i== 1)
			{
				autoupdateURL = updateUrlVO.getRegion2();
			}
			if(i== 2)
			{
				autoupdateURL = updateUrlVO.getRegion3();
			}
			try{
				if(autoupdateURL != null && !autoupdateURL.equals(""))
				{
					List<String> downloadList = updateList.DownLoadListProcess(autoupdateURL);
					if(downloadList.size() > 0)
						{
							String updatePath = new File(".").getCanonicalPath();
							updatePath = updatePath + "\\updates";
							
							updateList.DownloadFile(downloadList, updatePath, autoupdateURL);
							updateList.updateLocalCofig(autoupdateURL);
							logger.info("autoupdate succeffully: "+autoupdateURL);
							break;
						}else{
							logger.info("No download list :" +autoupdateURL );
						}
				}
			}catch(Exception e)
			{
				logger.error("Checkupdate: "+ autoupdateURL +">> "+ e.getMessage());
			}
			}
			
		}
		
	
	public UpdateURLVO readConfig(String filePath) {
		JsonParser jsonParser = new JsonParser();
		UpdateURLVO updateUrlVO = null;
		try{
			
			if(!(filePath.trim().equals(""))) {
				Object obj = jsonParser.parse(new FileReader(filePath));
				updateUrlVO = new Gson().fromJson(new Gson().toJson(obj), UpdateURLVO.class);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return updateUrlVO;
	}
	*/
}
