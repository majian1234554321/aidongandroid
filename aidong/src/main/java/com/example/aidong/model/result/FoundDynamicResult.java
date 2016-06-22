package com.example.aidong.model.result;

import com.example.aidong.model.Dynamic;

import java.util.ArrayList;


public class FoundDynamicResult extends MsgResult {
	
	private Data data;
	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data{
		private ArrayList<Dynamic> dynamic;

		public ArrayList<Dynamic> getDynamic() {
			return dynamic;
		}

		public void setDynamic(ArrayList<Dynamic> dynamic) {
			this.dynamic = dynamic;
		}
		
		
	}

}
