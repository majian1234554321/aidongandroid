package com.example.aidong.entity.model;

import java.util.ArrayList;

public class Users {
	private int mxid;
	private String name;
	private String avatar;
	private int gender;
	private String signature;
	private int age;
	protected ArrayList<Integer> tags;// 图标
	public int getMxid() {
		return mxid;
	}
	public void setMxid(int mxid) {
		this.mxid = mxid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public ArrayList<Integer> getTags() {
		return tags;
	}
	public void setTags(ArrayList<Integer> tags) {
		this.tags = tags;
	}
	
}
