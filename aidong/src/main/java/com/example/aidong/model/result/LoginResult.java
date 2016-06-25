package com.example.aidong.model.result;


import com.example.aidong.model.UserCoach;

public class LoginResult extends MsgResult {
	private LoginData data;
	
	public LoginData getData() {
		return data;
	}



	public void setData(LoginData data) {
		this.data = data;
	}



	public class LoginData{
		private UserCoach user;

		public UserCoach getUser() {
			return user;
		}

		public void setUser(UserCoach user) {
			this.user = user;
		}
		
	}
}
