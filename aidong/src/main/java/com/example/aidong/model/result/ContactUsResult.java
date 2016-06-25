package com.example.aidong.model.result;


public class ContactUsResult extends MsgResult{

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	private Data data;

	public class Data {
		private String phone;// 客服电话

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

	}
}
