package com.scan.service;
import java.io.UnsupportedEncodingException;

import com.hsm.barcode.DecodeResult;
import com.hsm.barcode.Decoder;
import com.hsm.barcode.DecoderConfigValues.SymbologyID;
import com.hsm.barcode.DecoderException;
import com.hsm.barcode.SymbologyConfig;
import com.scan.activity.Mservice;
import com.scan.util.Config;
import com.scan.util.UpdateApp;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
//import android.util.Log;


public class ScanService extends Service {
	
	private static final int INIT_OK = 1 ;
	private static final int  INIT_FAIL = -1 ;
	
	private Decoder mDecoder ;  // scan inital
	private DecodeResult mDecodeResult;  //scan result
	
	private String TAG = "ScanService"; //DEBUG
	
	private String charSetName = "utf-8";
	
	private boolean running = false ;// scaner open flag

	private Config config ;//读取配置列表
	
	private KillReceiver killReceiver ;//广播接受者用于接受打开和关闭的广播
	
	private SwicthMethod swM ;//切换输入法
	
	public static final  String ACTION_CLOSE_SCAN = "colseScan";
	
	public static final  String ACTION_START_SCAN = "startScan";
	@Override
	public void onCreate() {
		config = new Config(this);
		//注册广播接受者
		killReceiver  = new KillReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_CLOSE_SCAN);
		filter.addAction(ACTION_START_SCAN);
		registerReceiver(killReceiver, filter);
//		Log.e("onCreate", "onCreate-->registerReceiver");
		
		swM = new SwicthMethod(this);
		
		//读取软件的版本号
		UpdateApp up = new UpdateApp(this) ;
		Log.e("APP version ", up.getVersion()) ;
		super.onCreate();
	}
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return aidlScan;
	}
	
	@Override
	public void onDestroy() {
		if(killReceiver != null){
			unregisterReceiver(killReceiver);
		}
	
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		synchronized (aidlScan) {
			try{
				if(!running){
					swM.getDefualtInputM() ;//获取默认输入法
					running = true ;
					aidlScan.init();
					aidlScan.setOnResultListener(new IScanResult() {
						
						@Override
						public IBinder asBinder() {
							// TODO Auto-generated method stub
							return null;
						}
						
						@Override
						public void onListener(String barcode, byte[] bar) throws RemoteException {
//							Log.e("bar-------", barcode);
							Intent mIntent = new Intent(ScanService.this, Mservice.class);
							mIntent.putExtra("barcode", barcode);
							startService(mIntent);
							
						}
					});
				}else{
					swM.switchMethod() ;
					aidlScan.scan();
				}
				}catch(Exception e){
					
				}
		}

		return super.onStartCommand(intent, flags, startId);
	}
	
	
	boolean isOpening = false ;// 如果程序正在打开，则不能马上关闭
	/**
	 * 用于接收关闭信息
	 * @author pang
	 *
	 */
	private class KillReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
//			Log.e("KillReceiver", "KillReceiver ");
			try{
			if(intent.getAction().equals(ACTION_CLOSE_SCAN)){
//				Log.e("KillReceiver", "KillReceiver=== CLOSE");
				if(aidlScan != null){
						aidlScan.close();
						running = false ;
				}
			}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LogUtil.SaveException("KillReceiver()", e.toString()) ;
			}
			
		}
		
	}
	
	
	
	private  AidlIscan aidlScan = new AidlIscan();
	
	//远程调用接口
	class AidlIscan extends IScan.Stub{
		private IScanResult iResultLister ;
		private boolean scanning = false ;
		@Override
		public int init() throws RemoteException {
			mDecoder = new Decoder();
			mDecodeResult = new DecodeResult();
			try {
				mDecoder.connectDecoderLibrary();
				mDecoder.enableSymbology(SymbologyID.SYM_ALL);
				//����EAN13У��λ
				SymbologyConfig config = new SymbologyConfig(SymbologyID.SYM_EAN13);
				config.Flags = 5 ;
				config.Mask = 1 ;
				mDecoder.setSymbologyConfig(config);
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							mDecoder.startScanning();
							Thread.sleep(50);
							mDecoder.stopScanning();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							LogUtil.SaveException("scan()", e.toString()) ;
						}
						
						
					}
				}).start();

			} catch (DecoderException e) {
				LogUtil.SaveException("init()", e.toString()) ;
				e.printStackTrace();
				return INIT_FAIL;
			}
			return INIT_OK;
		}

		@Override
		public void close() throws RemoteException {
			running = false ;
			if(mDecoder != null){
				try {
					mDecoder.disconnectDecoderLibrary();
				} catch (DecoderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					LogUtil.SaveException("close()", e.toString()) ;
				}
			}
			
		}

		@Override
		public void scan() throws RemoteException {
			if(!scanning){
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						scanning = true ;
						if(mDecoder != null){
							try {//ɨ��
								mDecoder.waitForDecodeTwo(5000, mDecodeResult);
//								swM.retoreMethod();
								
								if(mDecodeResult.length > 0){
									//�ص�
									if(iResultLister != null){
//										Log.e("barcode", "listner");
										iResultLister.onListener(chineseHolder(mDecoder.getBarcodeByteData(), charSetName), mDecoder.getBarcodeByteData());
									}
									scanning = false ;
								}
								
							} catch (DecoderException e) {
								swM.retoreMethod();
								scanning = false ;
								e.printStackTrace();
								LogUtil.SaveException("scan()", e.toString()) ;
							} catch (RemoteException e) {
								swM.retoreMethod();
								scanning = false ;
								e.printStackTrace();
								LogUtil.SaveException("scan()", e.toString()) ;
							}
						}
						
					}
				}).start();
			}
			
		}

		@Override
		public void setOnResultListener(IScanResult iLister)
				throws RemoteException {
			
			iResultLister = iLister;
		}

		@Override
		public void setChar(String charSetName) throws RemoteException {
			ScanService.this.charSetName = charSetName ;
			
		}
		
	}
	
	private String chineseHolder(byte[] barcodeBytes, String charSetName){
		String data = null;
		try{
			if("GB2312".equals(charSetName) || "gb2312".equals(charSetName)){
				data = new String(barcodeBytes, 0, barcodeBytes.length, "gb2312");
			}else if("GBK".equals(charSetName) || "gbk".equals(charSetName)){
				data = new String(barcodeBytes, 0, barcodeBytes.length, "gbk");
			}else{
				data = new String(barcodeBytes, 0, barcodeBytes.length, "UTF-8");
			}
		}catch(Exception e){
			
			LogUtil.SaveException("chineseHolder", e.toString()) ;
			
		}
		
		return data ; 
	}

}
