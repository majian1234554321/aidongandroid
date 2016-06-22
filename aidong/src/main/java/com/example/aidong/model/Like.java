package com.example.aidong.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
@Table(name="Like")
public class Like {
	@Id(column="id")
	private int id;
	@Column(column="mxid")
	private String mxid;
	@Column(column="omxid")
	private String omxid;
	@Column(column="liketime")
	private String liketime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMxid() {
		return mxid;
	}
	public void setMxid(String mxid) {
		this.mxid = mxid;
	}
	public String getOmxid() {
		return omxid;
	}
	public void setOmxid(String omxid) {
		this.omxid = omxid;
	}
	public String getLiketime() {
		return liketime;
	}
	public void setLiketime(String liketime) {
		this.liketime = liketime;
	}
	
	
	
}
