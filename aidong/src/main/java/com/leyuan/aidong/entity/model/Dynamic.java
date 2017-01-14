package com.leyuan.aidong.entity.model;

import com.leyuan.aidong.entity.model.AttributeDynamics.LikeUser;

import java.io.Serializable;
import java.util.ArrayList;


public class Dynamic implements Serializable{
	private int id;
	private AttributeComment comments;
	private String content;
	private long created;
	private AttributeFilm film;
	private ArrayList<AttributeImages> image;
	private ArrayList<AttributeImages> images;
	private ArrayList<LikeUser> like_user;
	private long likes;
	private UserCoach publisher;
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public AttributeComment getComments() {
		return comments;
	}


	public void setComments(AttributeComment comments) {
		this.comments = comments;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public long getCreated() {
		return created;
	}


	public void setCreated(long created) {
		this.created = created;
	}


	public AttributeFilm getFilm() {
		return film;
	}


	public void setFilm(AttributeFilm film) {
		this.film = film;
	}


	public ArrayList<AttributeImages> getImage() {
		if(image ==null){
			return images;
		}
		return image;
	}


	public void setImage(ArrayList<AttributeImages> image) {
		this.image = image;
	}


	public ArrayList<LikeUser> getLike_user() {
		return like_user;
	}


	public void setLike_user(ArrayList<LikeUser> like_user) {
		this.like_user = like_user;
	}


	public long getLikes() {
		return likes;
	}


	public void setLikes(long likes) {
		this.likes = likes;
	}


	public UserCoach getPublisher() {
		return publisher;
	}


	public void setPublisher(UserCoach publisher) {
		this.publisher = publisher;
	}

	public AttributeFindDynamics translationToAttribute(){
		AttributeFindDynamics dynamic = new AttributeFindDynamics();
		dynamic.setUser(getPublisher());
		
		
		AttributeDynamics d = new AttributeDynamics();
		d.setComments(getComments());
		d.setContent(getContent());
		d.setCreated(getCreated());
		d.setFilm(getFilm());
		d.setImages(getImage());
		d.setLike_user(getLike_user());
		d.setLikes(getLikes());
		d.setNo(getId());
		
		dynamic.setDynamic(d);
		
		
		
		
		
		return dynamic;
	}


	@Override
	public String toString() {
		return "Dynamic{" +
				"id=" + id +
				", comments=" + comments +
				", content='" + content + '\'' +
				", created=" + created +
				", film=" + film +
				", image=" + image +
				", images=" + images +
				", like=" + like_user +
				", likes=" + likes +
				", publisher=" + publisher +
				'}';
	}
}
