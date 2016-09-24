package com.example.aidong.entity.model.result;

import com.example.aidong.entity.model.UserCoach;
import com.example.aidong.entity.model.Users;

import java.util.ArrayList;
import java.util.List;


public class FriendsResult extends MsgResult {
private Data data;
	
	public Data getData() {
		return data;
	}



	public void setData(Data data) {
		this.data = data;
	}



	public class Data{
		private List<UserCoach> profiles;
		private List<Users> users;
		private ArrayList<UserCoach> coaches;
		
		public ArrayList<UserCoach> getCoaches() {
			return coaches;
		}

		public void setCoaches(ArrayList<UserCoach> coaches) {
			this.coaches = coaches;
		}

		public List<Users> getUsers() {
			return users;
		}

		public void setUsers(List<Users> users) {
			this.users = users;
		}

		public List<UserCoach> getProfiles() {
			return profiles;
		}

		public void setProfiles(List<UserCoach> profiles) {
			this.profiles = profiles;
		}
		
	}
}
