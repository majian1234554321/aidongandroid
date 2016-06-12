package com.leyuan.lovesport.http;


import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.http.IHttpToastCallBack;

public class Logic {

	public Logic(){};
	
	public void doLogic(IHttpCallback callBack, IHttpTask tsk, int method, int requestCode, IHttpToastCallBack base){
		HttpConfig config = HttpConfig.getInstance();
		config.sendRequest(callBack, method, HttpConfig.JSON, tsk, requestCode, base);
	}
	
}
