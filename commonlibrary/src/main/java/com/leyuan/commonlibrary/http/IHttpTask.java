package com.leyuan.commonlibrary.http;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;


public class IHttpTask{
	String Url;
	List<BasicNameValuePair> p;
	Class cla;
	JSONObject json;
	public Map<String, Object[]> mapFiles;
	boolean isface = false;
//	File[] files;
	Map<String, String> headerMap;
	
	
	public IHttpTask(String Url, List<BasicNameValuePair> params, Class cla){
		this.Url = Url;
		this.p = params;
		this.cla = cla;
	}
	public IHttpTask(String Url, Map<String, String> headerMap ,List<BasicNameValuePair> params, Class cla){
		this.Url = Url;
		this.p = params;
		this.cla = cla;
		this.headerMap = headerMap;
	}
	
	public Map<String, String> getHeaderMap() {
		return headerMap;
	}
	public IHttpTask(String Url, List<BasicNameValuePair> params, Map<String, Object[]> files, Class cla){
		this.Url = Url;
		this.p = params;
		this.cla = cla;
		this.mapFiles =files;
	}
	
	public IHttpTask(String Url, List<BasicNameValuePair> params, Map<String, Object[]> files, Class cla, boolean isface){
		this.Url = Url;
		this.p = params;
		this.cla = cla;
		this.mapFiles =files;
		this.isface = isface;
	}
	

	public IHttpTask(String Url, JSONObject json, Class cla){
		this.Url = Url;
		this.json = json;
		this.cla = cla;
	}
	
	/**
	 * URL
	 */
	public String getSubUrl(){
		return Url;
		}
	
	
	public List<BasicNameValuePair> getparams(){
		return p;
	}
	
	public Class getmClass(){
		return cla;
		}

	public JSONObject getJson() {
		return json;
	}

	public Map<String, Object[]> getFiles() {
		return mapFiles;
	}
	
	
	
	
	/**
	 *鍙傛�?
	 */
//	String getArgs(){
//		if(p != null){
//			return p.getArgs();
//		}
//		return "";
//		
//	}
	/**
	 * 瑙ｆ瀽锟�??鑷繁鎺у埗鏄敤json杩樻槸xml
	 */
//	public abstract IHandler<?> getHandler(String result);
	
	/**
	 * 璇锋眰鍙傛暟
	 * @param params
	 */
//	abstract void setParams(T params);
}
