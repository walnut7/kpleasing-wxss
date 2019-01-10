package com.kpleasing.wxss.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;


public class FileUtil {
	
	private static final Logger logger = Logger.getLogger(FileUtil.class);
	
	public static void record(String str) throws FileNotFoundException {
		String path = "/opt/wxss/log/record.txt";
		File file=new File(path);
		FileOutputStream out = null;
		
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			out = new FileOutputStream(file, true);
			out.write(str.getBytes("utf-8"));
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if(null!=out) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
