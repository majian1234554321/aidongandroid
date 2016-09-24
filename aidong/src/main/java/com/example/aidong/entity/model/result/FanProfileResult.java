package com.example.aidong.entity.model.result;


import com.example.aidong.entity.model.FanProfile;

public class FanProfileResult extends MsgResult {
	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data {
		private FanProfile enthusiast;

		public FanProfile getUser() {
			return enthusiast;
		}

		public void setUser(FanProfile enthusiast) {
			this.enthusiast = enthusiast;
		}

	}
}
