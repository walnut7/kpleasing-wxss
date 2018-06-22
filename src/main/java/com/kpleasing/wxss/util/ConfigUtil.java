package com.kpleasing.wxss.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import org.jboss.logging.Logger;

public class ConfigUtil {
	private static Logger logger = Logger.getLogger(ConfigUtil.class);
	private static final URL WXSS_CONF_FILE = ConfigUtil.class.getResource("/wxss.properties");
	private static ConfigUtil instance;
	private Properties property = new Properties();  

	public synchronized static ConfigUtil getInstance() {
		if (instance == null) {
			try {
				instance = new ConfigUtil();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	
	public ConfigUtil() throws URISyntaxException {
		InputStream inStream = null;
		try {
			// 如果文件不存在，创建文件
			File file = new File(WXSS_CONF_FILE.toURI());
			if (!file.exists()) {
				logger.info("配置文件不存在！");
				file.createNewFile();
			}
			
			inStream = new BufferedInputStream(new FileInputStream(file));
			property.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(null != inStream) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
	
	
	public String getPropertyParam(String key) {
		return property.getProperty(key);
	}
}
