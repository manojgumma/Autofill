package com.enjaz.webautofill.service;

import org.springframework.stereotype.Component;

@Component
public class UpdateList {
	/*@Autowired
	private PropertyReader propertyReader;
	private static final Logger logger = Logger.getLogger(UpdateList.class);

	public List<String> DownLoadListProcess(String upgradeURL) throws Exception {
		List<String> downloadList = new ArrayList<String>();
		try {
			Gson gson = new Gson();

			 Read remote update JSON file 
			System.err.println(buildURL("autofill_update.json", upgradeURL));
			String json ="";
		
			 json = readFromUrl(buildURL("autofill_update.json", upgradeURL));
			
			VersionControl remoteObject = gson.fromJson(json,
					VersionControl.class);
			 Read local update JSON file 
			BufferedReader localUpdateStream = new BufferedReader(
					new FileReader(new File(".").getCanonicalPath()
							+ "/autofill_update.json"));
			VersionControl localObject = gson.fromJson(localUpdateStream,
					VersionControl.class);
			for (Dependencies remotedepend : remoteObject.getDependencies()) {
				 Get remote file version 
				Boolean isNewFile = true;
				DefaultArtifactVersion remoteVersion = new DefaultArtifactVersion(
						remotedepend.getVersion());
				for (Dependencies localdepend : localObject.getDependencies()) {
					if (remotedepend.getName().equalsIgnoreCase(
							localdepend.getName())) {
						 Get local file version 
						isNewFile = false;
						DefaultArtifactVersion localVersion = new DefaultArtifactVersion(
								localdepend.getVersion());
						if (remoteVersion.compareTo(localVersion) == 1) {
							downloadList.add(remotedepend.getFilename());
						}
					}
				}
				if (isNewFile) {
					downloadList.add(remotedepend.getFilename());
				}
			}
		} catch (Exception e) {
			logger.error("DownLoadListProcess >>", e);
			throw new Exception(e.getMessage());
		}
		return downloadList;
	}

	private static String readFromUrl(String urlString) throws Exception {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	public void DownloadFile(List<String> downloadList, String folderPath, String upgradeURL) {
		try {
			File f = new File(folderPath);
			if (!f.exists()) {
				f.mkdir();
			}
			String updateURL = upgradeURL;
			//String updateURL = "https://s3-eu-west-1.amazonaws.com/ebn/vfs/autofillUpdate/";
			String fileURL = "";
			for (String fileName : downloadList) {
				fileURL = buildURL(fileName, updateURL);
				HttpDownLoadUtility.downloadFile(fileURL, folderPath);
			}
		} catch (Exception e) {
			logger.error("DownloadFile>>", e);
		}

	}

	public Boolean updateLocalCofig(String upgradeURL) {
		try {
			String json = readFromUrl(buildURL("autofill_update.json", upgradeURL));
			File file = new File(new File(".").getCanonicalPath()
					+ "/autofill_update.json");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(json);
			bw.close();
		} catch (Exception ex) {
			return false;
		}
		return true;
	}
	
	private String buildURL(String fileName, String upgradeURL){
		String url = "";
		try {
			URIBuilder b;
			b = new URIBuilder();
			upgradeURL += "/"+fileName;
			//normalize
			URI uri = new URI(upgradeURL);
			upgradeURL = uri.normalize().toString();
			b.setPath(upgradeURL);
			url = b.build().toURL().toString();
			System.out.println(url.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}*/
}
