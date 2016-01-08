package com.scan.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 用于保存log信息于sdcard
 * @author admin
 *
 */
public class LogUtils {
	public static String LOG_FILE_NAME = "log.txt";
	public static String LOG_FILE_DIR = "/mnt/sdcard/ScanService/";
	
	/**
	 * 写入log信息
	 * @param logInfo
	 */
	public static void writeErrorLog(String logInfo){
		File file = new File(LOG_FILE_DIR);
		//创建文件夹
		if(!file.exists()){
			file.mkdir() ;
		}
		try {
//			File fos = new FileOutputStream(LOG_FILE_DIR + LOG_FILE_NAME);
			//直接写入，将之前的错误信息覆盖
			FileWriter fw = new FileWriter(new File(LOG_FILE_DIR + LOG_FILE_NAME) , false);
			fw.write(logInfo);
			fw.flush() ;
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
