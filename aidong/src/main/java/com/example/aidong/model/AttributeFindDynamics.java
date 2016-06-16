package com.example.aidong.model;

import java.io.Serializable;


public class AttributeFindDynamics implements Serializable{

	private AttributeDynamics dynamic;
	private UserCoach user;
	public AttributeDynamics getDynamic() {
		return dynamic;
	}
	public void setDynamic(AttributeDynamics dynamic) {
		this.dynamic = dynamic;
	}
	public UserCoach getUser() {
		return user;
	}
	public void setUser(UserCoach user) {
		this.user = user;
	}
	
}
