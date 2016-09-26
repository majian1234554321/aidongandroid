package com.leyuan.aidong.entity.model;

import java.io.Serializable;
import java.util.ArrayList;


public class AttributeDynamics implements Serializable{
	private int no;
	private String content;
	private long likes;
	private long created;
	private AttributeComment comments;
	private AttributeFilm film;
	private ArrayList<AttributeImages> images;
//	private ArrayList<String> images;
	private ArrayList<LikeUser> like_user;  
	
	
	
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getLikes() {
		return likes;
	}
	public void setLikes(long likes) {
		this.likes = likes;
	}
	public long getCreated() {
		return created;
	}
	public void setCreated(long created) {
		this.created = created;
	}
	public AttributeComment getComments() {
		return comments;
	}
	public void setComments(AttributeComment comments) {
		this.comments = comments;
	}
	public AttributeFilm getFilm() {
		return film;
	}
	public void setFilm(AttributeFilm film) {
		this.film = film;
	}
	public ArrayList<AttributeImages> getImages() {
		return images;
	}
	public void setImages(ArrayList<AttributeImages> images) {
		this.images = images;
	}
	
	public ArrayList<LikeUser> getLike_user() {
		return like_user;
	}
	public void setLike_user(ArrayList<LikeUser> like_user) {
		this.like_user = like_user;
	}

	public class LikeUser implements Serializable{
		private UserCoach user;

		public UserCoach getUser() {
			return user;
		}

		public void setUser(UserCoach user) {
			this.user = user;
		}

		@Override
		public String toString() {
			return "LikeUser{" +
					"user=" + user +
					'}';
		}
	}

	@Override
	public String toString() {
		return "AttributeDynamics{" +
				"no=" + no +
				", content='" + content + '\'' +
				", likes=" + likes +
				", created=" + created +
				", comments=" + comments +
				", film=" + film +
				", images=" + images +
				", like_user=" + like_user +
				'}';
	}
}
