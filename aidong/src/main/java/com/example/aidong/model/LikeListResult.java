package com.example.aidong.model;

import com.example.aidong.model.result.MsgResult;

import java.util.ArrayList;

public class LikeListResult extends MsgResult {

	
	private Data data;
	
	
	public Data getData() {
		return data;
	}


	public void setData(Data data) {
		this.data = data;
	}


	public class Data{
		private ArrayList<LikeData> like;

		public ArrayList<LikeData> getLike() {
			return like;
		}

		public void setLike(ArrayList<LikeData> like) {
			this.like = like;
		}
		
	}
}
