package com.example.aidong.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Comment implements Serializable{
	/** 评论人*/
	private UserCoach user;
	/** 评论内容*/
	private String content;
	/** 评论时间*/
	private String created;
	/** 评分*/
	private float score;
	/** 图片*/
	private ArrayList<AttributeImages> images;
	
	
	public ArrayList<AttributeImages> getImages() {
		return images;
	}
	public void setImages(ArrayList<AttributeImages> images) {
		this.images = images;
	}
	
	
	public UserCoach getUser() {
		return user;
	}
	public void setUser(UserCoach user) {
		this.user = user;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	
	
}
