package com.leyuan.aidong.entity.model.result;


import com.leyuan.aidong.entity.model.UserCoach;

public class LoginResult  {
//	private LoginData data;
	private UserCoach user;

//	public LoginData getData() {
//		return data;
//	}
//
//
//
//	public void setNurtureList(LoginData data) {
//		this.data = data;
//	}

	public UserCoach getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "LoginResult{" +
				"user=" + user +
				'}';
	}

	//	public class LoginData{
//		private UserCoach user;
//
//		public UserCoach getUser() {
//			return user;
//		}
//
//		public void setUser(UserCoach user) {
//			this.user = user;
//		}
//
//	}
}
