package com.leyuan.commonlibrary.http;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseJsonHandler<T> implements IHandler<T>{
	
	
	protected JSONObject obj;
	protected T extraData;
	public BaseJsonHandler(String result) throws JSONException{
	
		try{
		obj = new JSONObject(result);
		}catch(Exception e){
			//e.printStackTrace();
		}
		extraData = getParsedData();
	}
	
	//可能为null，注意捕获nullpoint
	public T getData(){
		return extraData;
	}

	@Override
	public abstract T getParsedData();
	
	
	
}
