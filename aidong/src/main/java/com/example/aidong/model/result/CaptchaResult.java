package com.example.aidong.model.result;

public class CaptchaResult extends MsgResult{
	private Data data;
	
	
	
	public Data getData() {
		return data;
	}



	public void setData(Data data) {
		this.data = data;
	}



	public class Data{
		public String token;

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}
		
	}
}
