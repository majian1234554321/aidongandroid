package com.example.aidong.entity.model.result;


import com.example.aidong .entity.model.UserCoach;

public class MxPersonalDataResult extends MsgResult {
	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data {
		private UserCoach user;
		private UserCoach profile;
		
		public UserCoach getProfile() {
			return profile;
		}

		public void setProfile(UserCoach profile) {
			this.profile = profile;
		}

		public UserCoach getUser() {
			return user;
		}

		public void setUser(UserCoach user) {
			this.user = user;
		}

	}

}
