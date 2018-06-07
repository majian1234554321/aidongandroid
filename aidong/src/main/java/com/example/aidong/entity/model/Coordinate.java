package com.example.aidong.entity.model;

import java.io.Serializable;

public class Coordinate implements Serializable{
	private double lng;// 经度
	private double lat;// 维度
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	
}
