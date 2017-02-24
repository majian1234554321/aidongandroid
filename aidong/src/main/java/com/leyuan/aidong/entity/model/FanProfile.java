package com.leyuan.aidong.entity.model;

import java.util.ArrayList;

public class FanProfile extends UserCoach {

//	/** 照片墙*/
//	private ArrayList<AttributeImages> photowall;
	/** 兴趣*/
	private ArrayList<String> hobby;
//	public ArrayList<AttributeImages> getPhotoWall() {
//		return photowall;
//	}
//	public void setPhotowall(ArrayList<AttributeImages> photowall) {
//		this.photowall = photowall;
//	}
	public ArrayList<String> getHobby() {
		return hobby;
	}
	public void setHobby(ArrayList<String> hobby) {
		this.hobby = hobby;
	}
	
	
}
