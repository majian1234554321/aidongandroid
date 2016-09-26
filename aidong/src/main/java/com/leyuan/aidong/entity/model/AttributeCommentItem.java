package com.leyuan.aidong.entity.model;

import java.io.Serializable;


public class AttributeCommentItem implements Serializable{
	private UserCoach user;
	private String content;
	private String created;
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
	
}
