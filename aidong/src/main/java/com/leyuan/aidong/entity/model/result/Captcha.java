package com.leyuan.aidong.entity.model.result;


import com.leyuan.aidong.entity.model.AttributeData;

public class Captcha extends MsgResult{

	
	public AttributeData data;
	
	private AttributeData getAttributeData(){
		if(data == null){
			data = new AttributeData();
		}
		return data;
	}
	public String getToken(){
		return getAttributeData().token;
	}	
	
	
	
//	public Captcha copyCpatchaValue(Captcha c){
//		Captcha object = new Captcha();
//		object.setCode(c.getCode());
//		object.setData(c.getData());
//		object.setMessage(c.getMessage());
//		return object;
//	}
}
