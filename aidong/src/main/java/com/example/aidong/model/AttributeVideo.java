package com.example.aidong.model;

import java.io.Serializable;


public class AttributeVideo implements Serializable{
	private String title;
	private long likes;
	private int comments;
	private AttributeFilm film;
	private int no;
	private String cover;
	private String content;
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getLikes() {
		return likes;
	}
	public void setLikes(long likes) {
		this.likes = likes;
	}
	public int getComments() {
		return comments;
	}
	public void setComments(int comments) {
		this.comments = comments;
	}
	public AttributeFilm getFilm() {
		return film;
	}
	public void setFilm(AttributeFilm film) {
		this.film = film;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	
}
