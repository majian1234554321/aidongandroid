package com.leyuan.aidong.entity.model;

import java.io.Serializable;

public class AttributeFilm implements Serializable{
	private String cover;
	private String film;
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getFilm() {
		return film;
	}
	public void setFilm(String film) {
		this.film = film;
	}
	
}
