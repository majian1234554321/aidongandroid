package com.example.aidong.model;

import java.io.Serializable;
import java.util.ArrayList;


public class AttributeComment implements Serializable{
	private int count;
	private long created;
	private String content;
	private ArrayList<AttributeCommentItem> item;
	private UserCoach user;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public long getCreated() {
		return created;
	}
	public void setCreated(long created) {
		this.created = created;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ArrayList<AttributeCommentItem> getItem() {
		return item;
	}
	public void setItem(ArrayList<AttributeCommentItem> item) {
		this.item = item;
	}
	public UserCoach getUser() {
		return user;
	}
	public void setUser(UserCoach user) {
		this.user = user;
	}
	
}
