package com.example.aidong.model.result;

import com.example.aidong.model.AttributeImages;

import java.util.ArrayList;


public class MxPhotosResult extends MsgResult{
	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data {
		public ArrayList<AttributeImages> photos;

		public ArrayList<AttributeImages> getPhotos() {
			return photos;
		}

		public void setPhotos(ArrayList<AttributeImages> photos) {
			this.photos = photos;
		}
		
	}
}
