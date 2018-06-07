package com.example.aidong.entity.model;

public class LikeData {
	private UserCoach user;
	private int count;
	private String created;
	public UserCoach getUser() {
		return user;
	}
	public void setUser(UserCoach user) {
		this.user = user;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	
}
