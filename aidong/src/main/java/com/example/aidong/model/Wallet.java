package com.example.aidong.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

@Table(name="Wallet")
public class Wallet implements Serializable{
	@Id(column="mxid")
	@NoAutoIncrement
	private int mxid;
	@Column(column = "balance")
	private float balance; //账户余额,
	@Column(column = "coupons")
	private int coupons; //优惠券数量,
	@Column(column = "bean")
	private int bean; //美豆
	@Column(column = "signtime")
	private String signtime;
	
	public String getSigntime() {
		return signtime;
	}
	public void setSigntime(String signtime) {
		this.signtime = signtime;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public int getCoupons() {
		return coupons;
	}
	public void setCoupons(int coupons) {
		this.coupons = coupons;
	}
	public int getBean() {
		return bean;
	}
	public void setBean(int bean) {
		this.bean = bean;
	}
	public int getMxid() {
		return mxid;
	}
	public void setMxid(int mxid) {
		this.mxid = mxid;
	}
	
	
}
