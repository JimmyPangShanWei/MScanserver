package com.scan.service;

import com.scan.scanner.Scanner;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
/***
 * 扫描服务
 * @author admin
 *
 */
public class MScanService extends Service {
	
	private Scanner scanner = null ;

	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		if(scanner != null){
			scanner.closeDev(); 
		}
		scanner = new Scanner() ;
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(!scanner.isInit){
			//初始化
			scanner.initDev() ;
		}else{
			scanner.scan() ;
		}
		
		return super.onStartCommand(intent, flags, startId);
	}

}
