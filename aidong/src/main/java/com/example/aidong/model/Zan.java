package com.example.aidong.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

@Table(name="zan")
public class Zan {
	@Id(column="id")
	private int id;
	@Column(column="mxid")
	private String mxid;
	@Column(column="dynamicid")
	private String dynamicid;
	@Column(column="iszan")
	private boolean iszan;
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
	public String getDynamicid() {
		return dynamicid;
	}
	public void setDynamicid(String dynamicid) {
		this.dynamicid = dynamicid;
	}
	public boolean isIszan() {
		return iszan;
	}
	public void setIszan(boolean iszan) {
		this.iszan = iszan;
	}

	
}
