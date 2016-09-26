package com.leyuan.aidong.entity.model.result;


import com.leyuan.aidong.entity.model.Dynamic;

public class DynamicDetailResult extends MsgResult{

	private Data data;
	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data {
		private Dynamic dynamic;

		public Dynamic getDynamic() {
			return dynamic;
		}

		public void setDynamic(Dynamic dynamic) {
			this.dynamic = dynamic;
		}
		
	}
	
}
