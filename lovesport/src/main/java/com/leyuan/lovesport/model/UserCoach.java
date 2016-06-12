package com.leyuan.lovesport.model;

import java.io.Serializable;

public class UserCoach implements Serializable {
	private String token; // '用户token值',
	private String name; // '昵称'，
	private int mxid; // 美型号,
	private String avatar; // 头像,
	private String avatar_url;
	private int gender; // 性别 0:男，1：女
	private int age; // 年龄
	private String signature;
	private int identity;
	private String mobile;
	protected int true_age; // 年龄
	private String birthday;
	private String address;
	private String target;
	private String skill;
	private String often;
	private String interests;
	private int likes;
	private double balance;
	private int order;
	private int appoint;
	private String business;
	private int clock;
	private int id; //添加学员的id
	
	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public int getClock() {
		return clock;
	}

	public void setClock(int clock) {
		this.clock = clock;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getOften() {
		return often;
	}

	public void setOften(String often) {
		this.often = often;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getAppoint() {
		return appoint;
	}

	public void setAppoint(int appoint) {
		this.appoint = appoint;
	}

	public int getTrue_age() {
		return true_age;
	}

	public void setTrue_age(int true_age) {
		this.true_age = true_age;
	}

//	public Contact1 getContact() {
//		return contact;
//	}
//
//	public void setContact(Contact1 contact) {
//		this.contact = contact;
//	}
	
	

	public String getToken() {
		return token;
	}

	public String getAvatar_url() {
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMxid() {
		return mxid;
	}

	public void setMxid(int mxid) {
		this.mxid = mxid;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public int getIdentity() {
		return identity;
	}

	public void setIdentity(int identity) {
		this.identity = identity;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
