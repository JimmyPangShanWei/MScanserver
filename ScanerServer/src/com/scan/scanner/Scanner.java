package com.scan.scanner;

import com.hsm.barcode.DecodeResult;
import com.hsm.barcode.Decoder;
import com.hsm.barcode.DecoderException;
import com.hsm.barcode.SymbologyConfig;
import com.hsm.barcode.DecoderConfigValues.SymbologyID;
import com.scan.service.LogUtil;

/**
 * 扫描
 * @author admin
 *
 */
public class Scanner implements IScaner{

	private Decoder mDecoder;  //设备
	private DecodeResult mDecodeResult;//扫描结果
	
	private String TAG = "class Scanner" ;

	/**
	 * 初始化设备
	 * @return
	 */
	@Override
	public int initDev() {
		mDecoder = new Decoder();
		mDecodeResult = new DecodeResult();
		try {
			//连接设备  connect dev
			mDecoder.connectDecoderLibrary();
			//设置所有码制可用  set all symbology enable
			mDecoder.enableSymbology(SymbologyID.SYM_ALL);
			//设置EN13码
			SymbologyConfig config = new SymbologyConfig(SymbologyID.SYM_EAN13);
			config.Flags = 5 ;
			config.Mask = 1 ;
			mDecoder.setSymbologyConfig(config);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						//触发扫描
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
			
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 关闭设备
	 */
	@Override
	public void closeDev() {
		LogUtil.LogE(TAG, "closeDev()") ;
		if(mDecoder != null){
			try {
				mDecoder.disconnectDecoderLibrary();
				mDecoder = null ;
			} catch (DecoderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LogUtil.SaveException("close()", e.toString()) ;
			}
		}
		
	}

	/***
	 * 调用扫描
	 */
	@Override
	public void scan() {
		// TODO Auto-generated method stub
		
	}

	/***
	 * 设置回调输出接口
	 */
	@Override
	public void setOnResultListener(IScanResult iLister) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 设置编码格式，主要是应对带中文的二维码
	 */
	@Override
	public void setChar(String charSetName) {
		// TODO Auto-generated method stub
		
	}

	
}
