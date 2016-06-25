package com.example.aidong.model.result;


import com.example.aidong.model.UserCoach;

public class CheckResult extends MsgResult {
	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data {
		private UserCoach user;

		public UserCoach getUser() {
			return user;
		}

		public void setUser(UserCoach user) {
			this.user = user;
		}

	}
}
