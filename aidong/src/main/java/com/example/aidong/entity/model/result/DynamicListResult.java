package com.example.aidong.entity.model.result;

import com.example.aidong .entity.model.Dynamic;

import java.util.ArrayList;


public class DynamicListResult extends MsgResult{
	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	
	public class Data {
		private ArrayList<Dynamic> dynamics;
		private ArrayList<Dynamic> dynamic;
//		private AttributeDynamicNew dynamic;
		public ArrayList<Dynamic> getDynamics() {
			return dynamics;
		}
		public void setDynamics(ArrayList<Dynamic> dynamics) {
			this.dynamics = dynamics;
		}
//		public AttributeDynamicNew getDynamic() {
//			return dynamic;
//		}
//		public void setDynamic(AttributeDynamicNew dynamic) {
//			this.dynamic = dynamic;
//		}
		public ArrayList<Dynamic> getDynamic() {
			return dynamic;
		}
		public void setDynamic(ArrayList<Dynamic> dynamic) {
			this.dynamic = dynamic;
		}
		
	}
}
