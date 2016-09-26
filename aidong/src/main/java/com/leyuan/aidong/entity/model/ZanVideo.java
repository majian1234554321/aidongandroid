package com.leyuan.aidong.entity.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

@Table(name="zan_video")
public class ZanVideo {
	@Id(column="id")
	private int id;
	@Column(column="mxid")
	private String mxid;
	@Column(column="videoid")
	private String videoid;
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
	public String getVideoid() {
		return videoid;
	}
	public void setVideoid(String videoid) {
		this.videoid = videoid;
	}
	public boolean isIszan() {
		return iszan;
	}
	public void setIszan(boolean iszan) {
		this.iszan = iszan;
	}
	
}
